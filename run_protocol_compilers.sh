mkdir generated 2>>log
mkdir generated/java 2>>log
mkdir generated/js 2>>log
mkdir generated/python 2>>log

# TODO --js_out=js generation is not allowed with optionals
# --experimental_allow_proto3_optional
# ➜  protobuf-specs git:(main) ✗ protoc --version -> libprotoc 3.12.4

protoc --experimental_allow_proto3_optional --python_out=generated/python --java_out=generated/java node/bifrost_rpc.proto genus/genus_rpc.proto

