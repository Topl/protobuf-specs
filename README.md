# protobuf-specs
Protobuf specifications and definitions representing blockchain data types and communication channels.


## Required tools

- protocol compiler: [protoc-installation](https://grpc.io/docs/protoc-installation/)


## Run
- Windows: 
    > TODO
- Linux: 
    > sh ./run_protocol_compilers.sh

## Developers
When testing changes, it helps to verify their behavior in the libraries that consome these protobuf specs.  You can publish the compiled protobuf as a "local" library and consume it in a different project.
### Scala
1. `cd build/scala`
1. `sbt publishLocal`
1. Check the logs to see the org/package/version that was published, and use as a normal SBT dependency in a different project

### Dart
1. `cd build/dart`
1. `sh compile_protos.sh`
1. Reference the `protobuf-specs/build/dart` directory as a pubspec file dependency
    ```
    dependencies:
      topl_protobuf:
        path: /path/to/protobuf-specs/build/dart
    ```