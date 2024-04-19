Seq(
  "com.github.sbt"          % "sbt-release"               % "1.1.0",
  "com.eed3si9n"            % "sbt-buildinfo"             % "0.11.0",
  "net.bzzt"                % "sbt-reproducible-builds"   % "0.30",
  "org.typelevel"           % "sbt-fs2-grpc"              % "2.7.11",
  "com.thesamet"            % "sbt-protoc"                % "1.0.6",
  "com.github.sbt"          % "sbt-ci-release"            % "1.5.12"
).map(addSbtPlugin)

libraryDependencies ++= Seq(
  "com.thesamet.scalapb" %% "compilerplugin"           % "0.11.15",
  "com.thesamet.scalapb" %% "scalapb-validate-codegen" % "0.3.4"
)
