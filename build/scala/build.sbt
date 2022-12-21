val scala213 = "2.13.10"

inThisBuild(
  List(
    organization := "co.topl",
    scalaVersion := scala213,
    versionScheme := Some("early-semver"),
    dynverSeparator := "-",
    version := dynverGitDescribeOutput.value.mkVersion(versionFmt, fallbackVersion(dynverCurrentDate.value)),
    dynver := {
      val d = new java.util.Date
      sbtdynver.DynVer.getGitDescribeOutput(d).mkVersion(versionFmt, fallbackVersion(d))
    }
  )
)

lazy val commonSettings = Seq(
  sonatypeCredentialHost := "s01.oss.sonatype.org",
  crossScalaVersions := Seq(scala213),
  resolvers ++= Seq(
    "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases/",
    "Sonatype Staging" at "https://s01.oss.sonatype.org/content/repositories/staging",
    "Sonatype Snapshots" at "https://s01.oss.sonatype.org/content/repositories/snapshots/",
    "Bintray" at "https://jcenter.bintray.com/"
  )
)

lazy val publishSettings = Seq(
  homepage := Some(url("https://github.com/Topl/Bifrost")),
  licenses := Seq("MPL2.0" -> url("https://www.mozilla.org/en-US/MPL/2.0/")),
  Test / publishArtifact := false,
  pomIncludeRepository := { _ => false },
  usePgpKeyHex("CEE1DC9E7C8E9AF4441D5EB9E35E84257DCF8DCB"),
  pomExtra :=
    <developers>
      <developer>
        <id>scasplte2</id>
        <name>James Aman</name>
      </developer>
      <developer>
        <id>tuxman</id>
        <name>Nicholas Edmonds</name>
      </developer>
    </developers>
)

def fallbackVersion(d: java.util.Date): String = s"HEAD-${sbtdynver.DynVer timestamp d}"

def versionFmt(out: sbtdynver.GitDescribeOutput): String = {
  val dirtySuffix = out.dirtySuffix.dropPlus.mkString("-", "")
  if (out.isCleanAfterTag) out.ref.dropPrefix + dirtySuffix // no commit info if clean after tag
  else out.ref.dropPrefix + out.commitSuffix.mkString("-", "-", "") + dirtySuffix
}

lazy val protobuf =
  project
    .in(file("."))
    .settings(
      moduleName := "protobuf",
      commonSettings,
      publish / skip := true,
      crossScalaVersions := Nil
    )
    .aggregate(
      protobufFs2
    )

lazy val copyProtobufTask = TaskKey[Unit]("copyProtobufTask", "Copy protobuf files from repository root")

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
        val repoRoot = Paths.get("").toAbsolutePath.getParent.getParent
        val allFiles =
          Files.walk(repoRoot).iterator()
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
            val destination = Paths.get(destinationBase.toString, protoFile.toString.drop(repoRoot.toString.length + 1))
            sLog.value.debug(s"Copying from $protoFile to $destination")
            Files.createDirectories(destination.getParent)
            Files.copy(protoFile, destination)
          }
      },
      (Compile / compile) := (Compile / compile).dependsOn(copyProtobufTask).value,
      // Consume the copied files from the task above
      Compile / PB.protoSources := Seq(new java.io.File(s"${(Compile / target).value.toString}/protobuf-tmp"))
    )
