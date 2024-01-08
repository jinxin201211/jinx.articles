package com.jinx.test.lock;

import java.util.concurrent.TimeUnit;

public class DeadLockTest {
    public static void main(String[] args) {
        final Object lock1 = new Object();
        final Object lock2 = new Object();

        new Thread(() -> {
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + "取得lock1");
                try {
                    TimeUnit.MILLISECONDS.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + "取得lock2");
                }
            }
        }, "Thread A").start();

        new Thread(() -> {
            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName() + "取得lock2");
                try {
                    TimeUnit.MILLISECONDS.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println(Thread.currentThread().getName() + "取得lock1");
                }
            }
        }, "Thread B").start();
    }
}
