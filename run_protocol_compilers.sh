# @rem sifdj sifdj s ask mark why we need to delete this? see windows cmd

mkdir java 2>z
mkdir js 2>z
mkdir python 2>z

# TODO --js_out=js generation is not allowed with optionals
# --experimental_allow_proto3_optional
# ➜  protobuf-specs git:(main) ✗ protoc --version -> libprotoc 3.12.4

protoc --experimental_allow_proto3_optional --python_out=python --java_out=java --proto_path=protobuf protobuf/node/bifrost_rpc.proto protobuf/genus/genus_rpc.proto

