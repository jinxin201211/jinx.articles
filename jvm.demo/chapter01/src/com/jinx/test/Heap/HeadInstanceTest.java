package com.jinx.test.Heap;

import java.util.ArrayList;
import java.util.Random;

public class HeadInstanceTest {
    byte[] buffer = new byte[new Random().nextInt(1024 * 1024)];

    public static void main(String[] args) {
        ArrayList<HeadInstanceTest> list = new ArrayList<>();
        while (true) {
            list.add(new HeadInstanceTest());
            try {
                Thread.sleep(100l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
