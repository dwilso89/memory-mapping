package dewilson.projects.memory.mapping;

public class MemoryLoaderTest {

    public static void main(final String[] args) {
        // get memory to load and reload time
        final long memoryToLoad = Long.parseLong(args[0]);
        final long readInterval = Long.parseLong(args[1]);

        new RandomMemoryLoader.Builder()
                .memoryToLoad(memoryToLoad)
                .readInterval(readInterval)
                .build()
                .start();
    }

}
