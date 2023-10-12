package com.jinx.test.Heap;

import java.util.List;
import java.util.Random;

public class OOMTests {
    public static void main(String[] args) {
        List<Picture> pictures = new java.util.ArrayList<Picture>();
        while (true) {
            try {
                Thread.sleep(200l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pictures.add(new Picture(new Random().nextInt(1024 * 1024)));
        }
    }

    static class Picture {
        byte[] bytes;

        public Picture(int length) {
            bytes = new byte[length];
        }
    }
}
