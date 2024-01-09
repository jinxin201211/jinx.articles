package com.jinx.test.lock;

import java.util.concurrent.TimeUnit;

public class LockSupportTest {
    public static void main(String[] args) {
//        t1();
//        t2();
        t3();
    }

    private static void t1() {
        Object obj = new Object();
        new Thread(() -> {
            synchronized (obj) {
                System.out.println("[" + Thread.currentThread().getName() + "]" + "ENTER");
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("[" + Thread.currentThread().getName() + "]" + "WAKEUP");
            }
        }, "T1").start();

        try {
            TimeUnit.MILLISECONDS.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            synchronized (obj) {
                System.out.println("[" + Thread.currentThread().getName() + "]" + "ENTER");
                obj.notify();
                System.out.println("[" + Thread.currentThread().getName() + "]" + "NOTIFY");
            }
        }, "T2").start();
    }

    private static void t2() {
        Object obj = new Object();
        new Thread(() -> {
            System.out.println("[" + Thread.currentThread().getName() + "]" + "ENTER");
            try {
                obj.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[" + Thread.currentThread().getName() + "]" + "WAKEUP");
        }, "T1").start();

        try {
            TimeUnit.MILLISECONDS.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("[" + Thread.currentThread().getName() + "]" + "ENTER");
            obj.notify();
            System.out.println("[" + Thread.currentThread().getName() + "]" + "NOTIFY");
        }, "T2").start();
    }

    private static void t3() {
        Object obj = new Object();
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (obj) {
                System.out.println("[" + Thread.currentThread().getName() + "]" + "ENTER");
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("[" + Thread.currentThread().getName() + "]" + "WAKEUP");
            }
        }, "T1").start();

        new Thread(() -> {
            synchronized (obj) {
                System.out.println("[" + Thread.currentThread().getName() + "]" + "ENTER");
                obj.notify();
                System.out.println("[" + Thread.currentThread().getName() + "]" + "NOTIFY");
            }
        }, "T2").start();
    }
}
