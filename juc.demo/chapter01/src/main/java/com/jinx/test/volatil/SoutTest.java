package com.jinx.test.volatil;

import java.util.concurrent.TimeUnit;

public class SoutTest {
    private static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            System.out.println("come in");
            while (flag) {
            }
            System.out.println("flag is false, thread stopped");
        }, "t1").start();

        TimeUnit.MILLISECONDS.sleep(2000L);

        flag = false;
        System.out.println("flag changed to false");
    }
}
