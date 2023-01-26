Seq(
  "com.github.sbt"          % "sbt-release"               % "1.1.0",
  "com.eed3si9n"            % "sbt-buildinfo"             % "0.11.0",
  "com.github.sbt"          % "sbt-ci-release"            % "1.5.11",
  "net.bzzt"                % "sbt-reproducible-builds"   % "0.30",
  "org.typelevel"           % "sbt-fs2-grpc"              % "2.4.12",
  "com.thesamet"            % "sbt-protoc"                % "1.0.3"
).map(addSbtPlugin)
