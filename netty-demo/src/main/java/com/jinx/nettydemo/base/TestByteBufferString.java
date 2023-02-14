package com.jinx.nettydemo.base;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static com.jinx.nettydemo.base.ByteBufferUtil.debugAll;

public class TestByteBufferString {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put("hello string".getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        debugAll(buffer);

        ByteBuffer hello = StandardCharsets.UTF_8.encode("gnirts olleh");
//        hello.flip();
        System.out.println((char) hello.get());
        System.out.println((char) hello.get());
        System.out.println((char) hello.get());
        System.out.println((char) hello.get());
        debugAll(hello);

        ByteBuffer wrp = ByteBuffer.wrap("hello string".getBytes(StandardCharsets.UTF_8));
        System.out.println((char) wrp.get());
        System.out.println((char) wrp.get());
        System.out.println((char) wrp.get());
        System.out.println((char) wrp.get());
        debugAll(wrp);

        System.out.println(StandardCharsets.UTF_8.decode(buffer));
        System.out.println(StandardCharsets.UTF_8.decode(hello));
        System.out.println(StandardCharsets.UTF_8.decode(wrp));
    }
}
