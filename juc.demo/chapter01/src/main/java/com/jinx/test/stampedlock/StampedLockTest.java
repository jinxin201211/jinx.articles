package com.jinx.test.stampedlock;

import com.jinx.tool.TestUtil;

import java.util.concurrent.locks.StampedLock;

public class StampedLockTest {
    static int number = 37;
    static StampedLock stampedLock = new StampedLock();

    public void write() {
        long stamp = stampedLock.writeLock();
        System.out.println(Thread.currentThread().getName() + "\t写线程准备修改");
        try {
            number += 13;
        } finally {
            stampedLock.unlockWrite(stamp);
        }
        System.out.println(Thread.currentThread().getName() + "\t写线程结束");
    }

    public void read() {
        long stamp = stampedLock.readLock();
        System.out.println(Thread.currentThread().getName() + "\t读线程准备");
        for (int i = 0; i < 4; i++) {
            TestUtil.sleep(1000l);
            System.out.println(Thread.currentThread().getName() + "\t正在获取中");
        }
        try {
            System.out.println(Thread.currentThread().getName() + "\t" + number);
        } finally {
            stampedLock.unlockRead(stamp);
        }
        System.out.println(Thread.currentThread().getName() + "\t读线程结束");
    }

    public void optimisticRead() {
        long stamp = stampedLock.tryOptimisticRead();
        System.out.println(Thread.currentThread().getName() + "\t乐观读线程准备\t" + stampedLock.validate(stamp));
        for (int i = 0; i < 4; i++) {
            TestUtil.sleep(1000l);
            System.out.println(Thread.currentThread().getName() + "\t正在获取中\t" + (i + 1) + "s后\t" + stampedLock.validate(stamp));
        }
        if (!stampedLock.validate(stamp)){
            System.out.println(Thread.currentThread().getName() + "\t被修改");
            long stamp2 = stampedLock.readLock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t" + number);
            } finally {
                stampedLock.unlockRead(stamp2);
            }
        }
        System.out.println(Thread.currentThread().getName() + "\t读线程结束");
    }

    public static void main(String[] args) {
        StampedLockTest stampedLockTest = new StampedLockTest();
        new Thread(() -> {
            stampedLockTest.optimisticRead();
        }, "read 1").start();
        TestUtil.sleep(1000);
        new Thread(() -> {
            stampedLockTest.write();
        }, "write").start();
        new Thread(() -> {
            stampedLockTest.optimisticRead();
        }, "read 2").start();
    }
}
