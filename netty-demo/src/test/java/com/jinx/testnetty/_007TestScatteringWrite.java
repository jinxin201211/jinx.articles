package com.jinx.testnetty;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class _007TestScatteringWrite {
    public static void main(String[] args) {
        ByteBuffer b1 = StandardCharsets.UTF_8.encode("one");
        ByteBuffer b2 = StandardCharsets.UTF_8.encode("two");
        ByteBuffer b3 = StandardCharsets.UTF_8.encode("three");
        ByteBuffer b4 = StandardCharsets.UTF_8.encode("go");
        try (FileChannel channel = new RandomAccessFile("src/main/resources/words2.txt", "rw").getChannel()) {
            channel.write(new ByteBuffer[]{b1, b2, b3});
            channel.write(new ByteBuffer[]{b1, b2, b3, b4});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
