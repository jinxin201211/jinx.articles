package com.jinx.test.jvm;

public class StackStruTest {
    public static void main(String[] args) {
//        int i = 2 + 3;
        int i = 2;
        int j = 3;
        int k = i + j;

        try {
            Thread.sleep(60000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
