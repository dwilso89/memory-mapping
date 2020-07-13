# memory-mapping
Investigating Java's Memory Mapped File API

## Build

Requires Java 1.8+

Designed to be built using Maven 3.x, such as ``` mvn package ```

## Execution

The scripts directory contains utilities for executing the Java programs. 

### Memory Map Files

Run __50__ processes which map the specified file, __/tmp/memoryMappedFile__ to memory. Then read random chunks of the file between __4096__ and __8192__ bytes every __100__ ms.

``` bash
./memoryMapping.sh 50 /tmp/memoryMappedFile 4096 8192 100
```

This script generates a script to kill the generated processes called __killProcesses.sh__

### Generate File

Generate a file called __file.txt__ with a size of __1500__ MiB.

``` bash
/generateFile.sh file.txt 1500
```

### Load Memory

Load random bytes with length __4294967296__ (4GiB) to the specified size and will read the memory every __1000__ ms.

``` bash
./memoryLoader.sh 4294967296 1000
```
