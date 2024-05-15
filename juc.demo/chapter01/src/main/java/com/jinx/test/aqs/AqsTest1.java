package com.jinx.test.aqs;

import com.jinx.tool.TestUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AqsTest1 {
    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock(true);
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
//                System.out.println("thread - 1 - " + i + " - waiting");
                reentrantLock.lock();
                try {
                    System.out.println("thread - 1 - " + i + " - enter");
                    TestUtil.sleep(1000l);
                } finally {
                    reentrantLock.unlock();
                }
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
//                System.out.println("thread - 2 - " + i + " - waiting");
                reentrantLock.lock();
                try {
                    System.out.println("thread - 2 - " + i + " - enter");
                    TestUtil.sleep(1000l);
                } finally {
                    reentrantLock.unlock();
                }
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
//                System.out.println("thread - 3 - " + i + " - waiting");
                reentrantLock.lock();
                try {
                    System.out.println("thread - 3 - " + i + " - enter");
                    TestUtil.sleep(1000l);
                } finally {
                    reentrantLock.unlock();
                }
            }
        }).start();
        CyclicBarrier barrier = new CyclicBarrier(1);
        CountDownLatch latch = new CountDownLatch(1);
        Semaphore semaphore = new Semaphore(1);
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    }
}
