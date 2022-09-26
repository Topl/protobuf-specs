@rem sifdj sifdj s

mkdir java 2>z
mkdir js 2>z
mkdir python 2>z
protoc --js_out=js --python_out=python --proto_path=protobuf protobuf/bifrost/bifrost_rpc.proto
