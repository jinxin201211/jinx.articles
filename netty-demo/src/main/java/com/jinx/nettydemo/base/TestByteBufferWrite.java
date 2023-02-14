package com.jinx.nettydemo.base;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestByteBufferWrite {
    public static void main(String[] args) {
        try (FileChannel channel = new FileOutputStream("src/main/resources/document.txt").getChannel()) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(10);
            byteBuffer.put(new byte[]{41, 42, 43, 44, 45, 46, 47, 48, 49});
            byteBuffer.flip();
            channel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
