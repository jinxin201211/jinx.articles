package com.jinx.testnio;

import java.nio.ByteBuffer;

import static com.jinx.nettydemo.base.ByteBufferUtil.debugAll;

public class _004TestByteBufferRead {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        byteBuffer.put(new byte[]{'a', 'b', 'c', 'd', 'e', 'f'});
        byteBuffer.flip();

        byteBuffer.get(new byte[6]);
        debugAll(byteBuffer);
        System.out.println(byteBuffer.hasRemaining());
        byteBuffer.rewind(); //从头开始读
        System.out.println(byteBuffer.hasRemaining());
        System.out.println((char) byteBuffer.get());
        byteBuffer.clear();
        System.out.println(byteBuffer.hasRemaining());
        System.out.println((char) byteBuffer.get());

        System.out.println("===================");
        // mark做标记，reset重置到mark的位置
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(16);
        byteBuffer2.put(new byte[]{'a', 'b', 'c', 'd', 'e', 'f'});
        byteBuffer2.flip();
        System.out.println((char) byteBuffer2.get());
        System.out.println((char) byteBuffer2.get());
        byteBuffer2.mark();
        System.out.println((char) byteBuffer2.get());
        System.out.println((char) byteBuffer2.get());
        System.out.println((char) byteBuffer2.get());
        System.out.println((char) byteBuffer2.get());
        debugAll(byteBuffer2);
        byteBuffer2.reset();
        debugAll(byteBuffer2);
        System.out.println((char) byteBuffer2.get());
        System.out.println((char) byteBuffer2.get(5));
    }
}
