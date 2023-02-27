package com.jinx.testnio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

import static com.jinx.nettydemo.base.ByteBufferUtil.debugAll;

public class _006TestScatteringRead {
    public static void main(String[] args) {
        try (FileChannel channel = new RandomAccessFile("src/main/resources/3parts.txt", "r").getChannel()) {
            ByteBuffer byteBuffer1 = ByteBuffer.allocate(3);
            ByteBuffer byteBuffer2 = ByteBuffer.allocate(3);
            ByteBuffer byteBuffer3 = ByteBuffer.allocate(5);
            channel.read(new ByteBuffer[]{byteBuffer1, byteBuffer2, byteBuffer3});
            debugAll(byteBuffer1);
            debugAll(byteBuffer2);
            debugAll(byteBuffer3);
            byteBuffer1.flip();
            System.out.println(StandardCharsets.UTF_8.decode(byteBuffer1));
            byteBuffer2.flip();
            System.out.println(StandardCharsets.UTF_8.decode(byteBuffer2));
            byteBuffer3.flip();
            System.out.println(StandardCharsets.UTF_8.decode(byteBuffer3));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
