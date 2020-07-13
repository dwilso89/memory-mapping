package dewilson.projects.memory.mapping;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

final class RandomMemoryLoader {

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 2;
    private final Random RANDOM = new Random();
    private final List<byte[]> memoryLoaded = new ArrayList<>();

    private final long memoryToLoad;
    private final long reloadTime;

    private RandomMemoryLoader(final long memoryToLoad, final long reloadTime) {
        this.memoryToLoad = memoryToLoad;
        this.reloadTime = reloadTime;
    }

    void start() {
        loadMemory();

        while (true) {
            if (this.reloadTime > 0L) {
                try {
                    Thread.sleep(this.reloadTime);
                    readMemory();
                } catch (final InterruptedException ie) {
                    System.out.println("ERROR: Interrupted sleep after memory reload...");
                    ie.printStackTrace();
                }
            }
        }
    }

    private void loadMemory() {
        long totalToRead = this.memoryToLoad;
        while (totalToRead > 0) {
            final byte[] randomBytes;
            if (totalToRead >= MAX_ARRAY_SIZE) {
                randomBytes = new byte[MAX_ARRAY_SIZE];
                totalToRead = totalToRead - MAX_ARRAY_SIZE;
            } else {
                randomBytes = new byte[(int) totalToRead];
                RANDOM.nextBytes(randomBytes);
                totalToRead = 0L;
            }
            System.out.println(String.format("Loaded array with length: %s", randomBytes.length));
            this.memoryLoaded.add(randomBytes);
        }
        System.out.println(String.format("Loaded total random bytes: %s", this.memoryToLoad));

    }

    private void readMemory() {
        System.out.println("Reading bytes...");
        this.memoryLoaded.forEach(bytes -> {
            int len = 0;
            for(byte b : bytes){
                len++;
            }
            System.out.println("Length read is: " + len);
        });
    }

    static final class Builder {

        private long memoryToLoad = 1024 * 1024;

        private long reloadTime = 0L;

        Builder() {

        }

        RandomMemoryLoader build() {
            return new RandomMemoryLoader(this.memoryToLoad, this.reloadTime);
        }

        Builder memoryToLoad(long memoryToLoad) {
            this.memoryToLoad = memoryToLoad;
            return this;
        }

        Builder reloadTime(long reloadTime) {
            this.reloadTime = reloadTime;
            return this;
        }


    }

}
