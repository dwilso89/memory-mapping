#!/usr/bin/env bash

# args
# 1 - number of processes to execute
# 2 - file to memory map
# 3 - max bytes to read at any given time
# 4 - min bytes to read at any give time
# 5 - time to sleep between reads

echo "#!/usr/bin/env bash" > killProcesses.sh

for i in $(seq 1 $1); do {
  java -Xmx16m -cp '../target/memory-mapping-1.0-SNAPSHOT.jar' dewilson.projects.memory.mapping.MemoryMappingTest $2 $3 $4 $5 & echo $!
  echo "kill " $! >> killProcesses.sh &
} done