mkdir generated 2>>log
mkdir generated/java 2>>log
mkdir generated/js 2>>log
mkdir generated/python 2>>log

protoc \
    --python_out=generated/python \
    --java_out=generated/java \
    --proto_path . \
    -Iproto/ \
    $(find . -iname "*.proto")
