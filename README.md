![GitHub tag (latest SemVer)](https://img.shields.io/github/v/tag/topl/protobuf-specs?label=release&style=plastic)

# protobuf-specs
Protobuf specifications and definitions representing blockchain data types and communication channels.

The artifact will be pushed from this repo to multiple repositories. 


## Required tools
- protocol compiler: [protoc-installation](https://grpc.io/docs/protoc-installation/)

## Run
- Windows:
  > TODO
- Linux:
  > sh ./run_protocol_compilers.sh
  
##  JitPack

This repo can be consumed using jitpack. Here is how:

- [JitPack](https://jitpack.io/#Topl/protobuf-specs) produces an artifact on each time is pushed. commit/branch

Add jitpack to the resolvers list in build.sbt. It should look like this:

```sbt
  resolvers ++= Seq("jitpack" at "https://jitpack.io")

  libraryDependencies += "com.github.Topl.protobuf-specs" %% "protobuf-fs2" % "e03a093"
```

## Maven Release

This repo can be consumed using Sonatype s01 releases. Here is how:

- Each time a tag is pushed, it produces an artifact: releases: [releases](https://s01.oss.sonatype.org/content/repositories/snapshots/co/topl/protobuf-fs2_2.13/)
- Each time a commit is pushed, it produces a snapshot artifact: [snapshots](https://s01.oss.sonatype.org/content/repositories/snapshots/co/topl/protobuf-fs2_2.13/)

Add Sonatype to the resolvers list in build.sbt. It should look like this:

```sbt
  resolvers ++= Seq(
    "Sonatype Staging" at "https://s01.oss.sonatype.org/content/repositories/staging",
    "Sonatype Snapshots" at "https://s01.oss.sonatype.org/content/repositories/snapshots/",
    "Sonatype Releases" at "https://s01.oss.sonatype.org/content/repositories/releases/"
  )

 libraryDependencies +=  "co.topl" %% "protobuf-fs2" % "2.0.0-alpha2"
```

## Developers
When testing changes, it helps to verify their behavior in the libraries that consume these protobuf specs.  You can publish the compiled protobuf as a "local" library and consume it in a different project.
### Scala
1. `cd build/scala`
1. `sbt publishLocal`
1. Check the logs to see the org/package/version that was published, and use as a normal SBT dependency in a different project
  - i.e. `"co.topl" %% "protobuf-fs2" % "b56d2815"`

### Dart
1. Install Dart [protoc_plugin](https://pub.dev/packages/protoc_plugin)
1. `cd build/dart`
1. `sh compile_protos.sh`
1. Reference the `protobuf-specs/build/dart` directory as a pubspec file dependency
    ```
    dependencies:
      topl_protobuf:
        path: /path/to/protobuf-specs/build/dart
    ```
