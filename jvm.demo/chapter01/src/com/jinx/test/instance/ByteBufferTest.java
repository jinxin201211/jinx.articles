package com.jinx.test.instance;

import java.nio.ByteBuffer;
import java.util.Scanner;

public class ByteBufferTest {
    private static final int SIZE = 1024 * 1024 * 1024;

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(SIZE);
        System.out.println("直接内存分配完成");

        Scanner scanner = new Scanner(System.in);
        scanner.next();

        System.out.println("直接内存开始释放");
        byteBuffer = null;
        System.gc();
        System.out.println("直接内存释放完成");
    }
}
