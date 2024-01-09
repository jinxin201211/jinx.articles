package com.jinx.test.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockSupportTest2 {
    public static void main(String[] args) {
        t1();
    }

    private static void t1() {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("[" + Thread.currentThread().getName() + "]" + "ENTER");
                condition.await();
                System.out.println("[" + Thread.currentThread().getName() + "]" + "WAKEUP");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "T1").start();

        try {
            TimeUnit.MILLISECONDS.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("[" + Thread.currentThread().getName() + "]" + "ENTER");
                condition.signal();
                System.out.println("[" + Thread.currentThread().getName() + "]" + "NOTIFY");
            } finally {
                lock.unlock();
            }
        }, "T2").start();
    }
}
