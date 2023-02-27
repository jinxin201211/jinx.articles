package com.jinx.testnio;

import java.nio.ByteBuffer;

public class _002TestByteBufferReadWrite {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.put((byte) 0x61);
        ByteBufferUtil.debugAll(byteBuffer);

        byteBuffer.put(new byte[]{(byte) 0x62, (byte) 0x63, (byte) 0x64});
        ByteBufferUtil.debugAll(byteBuffer);

        byteBuffer.flip();
        byteBuffer.get();
        byteBuffer.get();
        byteBuffer.compact();
        byteBuffer.put(new byte[]{(byte) 0x65, (byte) 0x66, (byte) 0x67});
        ByteBufferUtil.debugAll(byteBuffer);

        byteBuffer.flip();
        byteBuffer.get();
        byteBuffer.get();
        byteBuffer.clear();
        byteBuffer.put(new byte[]{(byte) 0x68, (byte) 0x69, (byte) 0x6a});
        ByteBufferUtil.debugAll(byteBuffer);
    }
}
