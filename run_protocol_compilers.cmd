@rem sifdj sifdj s

mkdir java
mkdir python


@rem Compile the PB models in order of dependency
for %%d in (common,quivr,ledger,node,genus,user) do (
    for %%f in (protobuf\%%d\*) do (protoc --python_out=python --java_out=java --proto_path=protobuf %%~f)
)

