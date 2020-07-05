package dewilson.projects.memory.mapping;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;

public class MemoryMappingTest {

    public static void main(final String[] args) throws Exception {
        // get fileName
        final String fileName = args[0];
        // get min/max bytes to read per cycle
        final int minBytesToRead = Integer.parseInt(args[1]);
        final int maxBytesToRead = Integer.parseInt(args[2]);
        // get time to sleep between reads
        final long sleepTime = Long.parseLong(args[3]);

        if (minBytesToRead > maxBytesToRead) {
            throw new IllegalArgumentException(
                    String.format("Min bytes to read [%s] cannot be larger than max bytes to read [%s].",
                            minBytesToRead, maxBytesToRead));
        }

        // open file channel
        final FileChannel fileChannel = new RandomAccessFile(new File(fileName), "r").getChannel();
        final long fileSize = validateAndGetFileSize(fileChannel);

        // read into shared memory space
        final ByteBuffer sharedMemoryBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, fileSize);

        // create random reader
        final RandomBufferReader randomBufferReader = new RandomBufferReader.Builder()
                .setBuffer(sharedMemoryBuffer)
                .setMaxBytesToRead(maxBytesToRead)
                .setMinBytesToRead(minBytesToRead)
                .setSize(fileSize)
                .build();

        // read bytes until killed
        while (true) {
            randomBufferReader.read();
            Thread.sleep(sleepTime);
        }
    }

    private static long validateAndGetFileSize(final FileChannel fileChannel) throws IOException {
        final long fileSize = fileChannel.size();

        if (fileSize > Integer.MAX_VALUE) {
            throw new UnsupportedOperationException("Files over size of max int (4GB) are not supported...");
        }

        System.out.println(String.format("File size: %s MB",
                new DecimalFormat("0.00").format(fileSize / 1024.0 / 1024.0)));

        return fileSize;
    }

}
