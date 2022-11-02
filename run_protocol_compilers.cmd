@rem sifdj sifdj s

mkdir java 2>z
mkdir js 2>z
mkdir python 2>z
protoc --js_out=js --python_out=python --java_out=java --proto_path=protobuf protobuf/bifrost/bifrost_rpc.proto protobuf/genus/genus_rpc.proto
