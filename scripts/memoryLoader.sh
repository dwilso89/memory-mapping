#!/usr/bin/env bash

# args
# 1 - memory to load (bytes)
# 2 - interval to read memory (ms)

((MAX=$1/1024/1024+4096))

echo Executing with Xmx set to [$MAX] MB

java -Xmx${MAX}m -cp '../target/memory-mapping-1.0-SNAPSHOT.jar' dewilson.projects.memory.mapping.MemoryLoaderTest $1 $2