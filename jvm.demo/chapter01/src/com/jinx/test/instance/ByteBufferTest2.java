package com.jinx.test.instance;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ByteBufferTest2 {
    private static final int SIZE = 1024 * 1024 * 20;

    public static void main(String[] args) {
        ArrayList<ByteBuffer> arrayList = new ArrayList<>();
        int count = 0;
        try {
            while (true) {
                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(SIZE);
                arrayList.add(byteBuffer);
                count++;
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            System.out.println(count);
        }
    }
}
