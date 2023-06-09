val scala213 = "2.13.10"

inThisBuild(
  List(
    organization := "co.topl",
    scalaVersion := scala213
  )
)

lazy val commonSettings = Seq(
  crossScalaVersions := Seq(scala213)
)

lazy val publishSettings = Seq(
  homepage := Some(url("https://github.com/Topl/protobuf-specs")),
  licenses := Seq("MPL2.0" -> url("https://www.mozilla.org/en-US/MPL/2.0/")),
  sonatypeCredentialHost := "s01.oss.sonatype.org",
  sonatypeRepository := "https://s01.oss.sonatype.org/service/local",
  Test / publishArtifact := false,
  pomIncludeRepository := { _ => false },
  pomExtra :=
    <developers>
      <developer>
        <id>mgrand-topl</id>
        <name>Mark Grand</name>
      </developer>
      <developer>
        <id>SeanCheatham</id>
        <name>Sean Cheatham</name>
      </developer>
      <developer>
        <id>nandotorterolo</id>
        <name>Fernando Torterolo</name>
      </developer>
      <developer>
        <id>scasplte2</id>
        <name>James Aman</name>
      </developer>
    </developers>
)

lazy val protobuf =
  project
    .in(file("."))
    .settings(
      moduleName := "protobuf",
      commonSettings,
      // publish / skip := true,
      crossScalaVersions := Nil
    )
    .aggregate(
      protobufFs2
    )

lazy val copyProtobufTask =
  TaskKey[Unit]("copyProtobufTask", "Copy protobuf files from repository root")

lazy val protobufFs2 =
  project
    .in(file("protobuf-fs2"))
    .enablePlugins(BuildInfoPlugin, Fs2Grpc)
    .settings(
      name := "protobuf-fs2",
      commonSettings,
      publishSettings,
      buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
      buildInfoPackage := "co.topl.buildinfo.protobuffs2",
      libraryDependencies ++= Seq(
        "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf",
        "com.thesamet.scalapb" %% "scalapb-validate-core" % scalapb.validate.compiler.BuildInfo.version % "protobuf"
      ),
      // This task copies all .proto files from the repository root into a directory that can be referenced by ScalaPB
      copyProtobufTask := {
        import java.nio.file._
        import scala.jdk.CollectionConverters._
        // The files will be copied into protobuf-fs2/target/protobuf-tmp
        val destinationBase = Paths.get((Compile / target).value.toString, "protobuf-tmp")
        // First, delete the existing tmp directory
        sLog.value.debug(s"Clearing protobuf-tmp directory=$destinationBase")
        if (Files.exists(destinationBase)) {
          Files.walk(destinationBase).sorted(java.util.Comparator.reverseOrder[Path]())
            .iterator()
            .asScala
            .foreach(Files.delete)
        }
        // Now, assemble a list of all of the .proto files in the repository root
        val protosRoot = Paths.get(Paths.get("").toAbsolutePath.getParent.getParent.toString, "proto")
        val allFiles =
          Files.walk(protosRoot).iterator()
            .asScala
            .map(_.toAbsolutePath)
            .toList
        val protoFiles =
          allFiles.filter(_.toString.endsWith(".proto"))
        // Copy each of the .proto files into the tmp directory
        sLog.value.info(s"Copying ${protoFiles.length} protobuf files to target/protobuf-tmp directory")
        protoFiles
          .foreach { protoFile =>
            // Preserve the directory structure when copying
            val destination = Paths.get(destinationBase.toString, protoFile.toString.drop(protosRoot.toString.length + 1))
            sLog.value.debug(s"Copying from $protoFile to $destination")
            Files.createDirectories(destination.getParent)
            val contents = new String(Files.readAllBytes(protoFile), "UTF-8")
            val modifiedContents = modifyProtoContents(contents)
            Files.write(destination, modifiedContents.getBytes("UTF-8"))
          }
      },
      (Compile / compile) := (Compile / compile).dependsOn(copyProtobufTask).value,
      (Compile / buildInfo) := (Compile / buildInfo).dependsOn(Compile / PB.generate).value,
      // Consume the copied files from the task above
      Compile / PB.protoSources := Seq(new java.io.File(s"${(Compile / target).value.toString}/protobuf-tmp")),
      // By default, "managed sources" (the generated protobuf scala files) do not publish their source code,
      // so this step includes generated sources in the published package
      Compile / packageSrc / mappings ++= {
        val base = (Compile / sourceManaged).value
        val files = (Compile / managedSources).value
        files.map { f => (f, f.relativeTo(base).get.getPath) }
      },
      scalapbCodeGeneratorOptions += CodeGeneratorOption.FlatPackage,
      Compile / PB.targets := scalapbCodeGenerators.value
        .map(_.copy(outputPath = (Compile / sourceManaged).value))
        .:+(scalapb.validate.gen(scalapb.GeneratorOption.FlatPackage) -> (Compile / sourceManaged).value: protocbridge.Target)
    )

/**
 * Instead of embedding scala-specific overrides in the protobuf files, we copy+modify the contents to embed any
 * Scala-specific code.
 * @param contents The contents of the original protobuf file
 * @return Updated contents, with scalapb validation included
 */
def modifyProtoContents(contents: String): String = {
    val syntaxStr = "syntax = \"proto3\";"
    val index = contents.indexOf(syntaxStr)
    require(index >= 0, s"Could not find $syntaxStr in protobuf file")
      s"""${contents.substring(0, index)}$syntaxStr
         |import "scalapb/scalapb.proto";
         |import "scalapb/validate.proto";
         |${if(!contents.contains("validate/validate.proto")) "import \"validate/validate.proto\";" else ""}
         |${contents.substring(index + syntaxStr.length)}
         |option (scalapb.options) = {
         |  [scalapb.validate.file] {
         |    validate_at_construction: true
         |  }
         |  field_transformations: [
         |    {
         |      when: {options: {[validate.rules] {message: {required: true}}}}
         |      set: {
         |        [scalapb.field] {
         |          required: true
         |        }
         |      }
         |    }
         |  ]
         |};
         |""".stripMargin
}
