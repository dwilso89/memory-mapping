package dewilson.projects.memory.mapping;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;

final class RandomBufferReader {

    private final Random random = new Random();
    private final byte[] bytes;
    private int maxBytes;
    private int minBytes;
    private ByteBuffer buffer;
    private long fileSize;

    private RandomBufferReader(final ByteBuffer buffer, final int maxBytesToRead, final int minBytesToRead, final long size) {
        this.maxBytes = maxBytesToRead;
        this.minBytes = minBytesToRead;
        this.buffer = buffer;
        this.fileSize = size;
        this.bytes = new byte[this.maxBytes];
    }

    void read() {
        // reset array to all zeros
        Arrays.fill(this.bytes, (byte) 0);

        // random offset and random length to read
        final int randomOffset = random.nextInt((int) this.fileSize - this.maxBytes);
        final int randomLength = random.nextInt((this.maxBytes - this.minBytes + 1)) + this.minBytes;
        // reset buffer position
        this.buffer.position(randomOffset);
        // read bytes
        this.buffer.get(bytes, 0, randomLength);

        // do something with bytes?
        // assert entire array is not zeros?
        verifyBytesRead();
    }

    byte[] readAndGet() {
        read();
        final byte[] copy = new byte[this.bytes.length];
        System.arraycopy(this.bytes, 0, copy, 0, this.bytes.length);
        return copy;
    }

    private void verifyBytesRead() {
        for (int i = 0; i < this.bytes.length; i++) {
            if (this.bytes[i] != 0) {
                // found non-zero byte. breaking...
                break;
            }
            if (i == this.bytes.length - 1) {
                throw new RuntimeException("No bytes read... or very unlucky chunk read");
            }
        }
    }

    static final class Builder {
        private int maxBytesToRead = 1024 * 8;
        private int minBytesToRead = 1024 * 2;
        private ByteBuffer buffer;
        private long size;

        final RandomBufferReader build() {
            return new RandomBufferReader(this.buffer, this.maxBytesToRead, this.minBytesToRead, this.size);
        }

        Builder setMaxBytesToRead(int maxBytesToRead) {
            this.maxBytesToRead = maxBytesToRead;
            return this;
        }

        Builder setMinBytesToRead(int minBytesToRead) {
            this.minBytesToRead = minBytesToRead;
            return this;
        }

        Builder setBuffer(ByteBuffer buffer) {
            this.buffer = buffer;
            return this;
        }

        Builder setSize(long size) {
            this.size = size;
            return this;
        }

    }

}
