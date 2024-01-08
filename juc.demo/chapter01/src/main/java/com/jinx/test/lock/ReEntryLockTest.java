package com.jinx.test.lock;

public class ReEntryLockTest {
    public synchronized void m1() {
        System.out.println("[" + Thread.currentThread().getName() + "]" + "m1 in");
        m2();
        System.out.println("[" + Thread.currentThread().getName() + "]" + "m1 out");
    }

    public synchronized void m2() {
        System.out.println("[" + Thread.currentThread().getName() + "]" + "m2 in");
        m3();
        System.out.println("[" + Thread.currentThread().getName() + "]" + "m2 out");
    }

    public synchronized void m3() {
        System.out.println("[" + Thread.currentThread().getName() + "]" + "m3 in");
        System.out.println("[" + Thread.currentThread().getName() + "]" + "m3 out");
    }

    public static void main(String[] args) {
        ReEntryLockTest reEntryLockTest = new ReEntryLockTest();
        new Thread(() -> {
            reEntryLockTest.m1();
        }).start();
    }
}
