package dewilson.projects.memory.mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

final class RandomMemoryLoader {

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 2;
    private final Random RANDOM = new Random();
    private final List<byte[]> memoryLoaded = new ArrayList<>();

    private final long memoryToLoad;
    private final long readInterval;

    private RandomMemoryLoader(final long memoryToLoad, final long readInterval) {
        this.memoryToLoad = memoryToLoad;
        this.readInterval = readInterval;
    }

    void start() {
        loadMemory();

        while (true) {
            if (this.readInterval > 0L) {
                try {
                    Thread.sleep(this.readInterval);
                    readMemory();
                } catch (final InterruptedException ie) {
                    System.out.println("ERROR: Interrupted sleep during memory read...");
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
            System.out.println(String.format("\tLength read is: %s", bytes.length));
        });
    }

    static final class Builder {

        private long memoryToLoad = 1024 * 1024;

        private long readInterval = 0L;

        Builder() {

        }

        RandomMemoryLoader build() {
            return new RandomMemoryLoader(this.memoryToLoad, this.readInterval);
        }

        Builder memoryToLoad(long memoryToLoad) {
            this.memoryToLoad = memoryToLoad;
            return this;
        }

        Builder readInterval(long readInterval) {
            this.readInterval = readInterval;
            return this;
        }

    }

}
