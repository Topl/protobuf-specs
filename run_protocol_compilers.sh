#!/bin/sh

mkdir generated 2>>log
mkdir generated/java 2>>log
mkdir generated/js 2>>log
mkdir generated/python 2>>log

cd proto

protoc \
    --python_out=../generated/python \
    --java_out=../generated/java \
    -I . \
    -I ../external_proto \
    $(find . -name '*.proto')
