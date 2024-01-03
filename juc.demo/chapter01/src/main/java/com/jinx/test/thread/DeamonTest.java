package com.jinx.test.thread;

public class DeamonTest {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().isDaemon());
            while (true) {
            }
        }, "t1");
        t1.setDaemon(true);
        t1.start();
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().isDaemon());
    }
}
