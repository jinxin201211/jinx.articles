package com.jinx.nettydemo.base;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class TestScatteringWrite {
    public static void main(String[] args) {
        ByteBuffer b1 = StandardCharsets.UTF_8.encode("one");
        ByteBuffer b2 = StandardCharsets.UTF_8.encode("two");
        ByteBuffer b3 = StandardCharsets.UTF_8.encode("three");
        try (FileChannel channel = new RandomAccessFile("src/main/resources/words2.txt", "rw").getChannel()) {
            channel.write(new ByteBuffer[]{b1, b2, b3});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
