package com.jinx.nettydemo.base;

import java.nio.ByteBuffer;

public class TestByteBufferAllocate {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(256);
        System.out.println(ByteBuffer.allocate(16).getClass()); // class java.nio.HeapByteBuffer java堆内存，读写效率较低，会收到GC的影响
        System.out.println(ByteBuffer.allocateDirect(16).getClass()); // class java.nio.DirectByteBuffer 直接内存，读写效率高（少一次拷贝），不被GC影响，分配内存的效率低，使用不当容易造成内存泄漏
    }
}
