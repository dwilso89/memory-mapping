package dewilson.projects.memory.mapping;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;

public class MemoryLoaderTest {

    public static void main(final String[] args) throws Exception {
        // get memory to load and reload time
        final long memoryToLoad = Long.parseLong(args[0]);
        final long reloadTime = Long.parseLong(args[1]);

        new RandomMemoryLoader.Builder()
                .memoryToLoad(memoryToLoad)
                .reloadTime(reloadTime)
                .build()
                .start();
    }

}
