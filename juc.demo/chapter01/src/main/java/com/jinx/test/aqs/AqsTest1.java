package com.jinx.test.aqs;

import com.jinx.tool.TestUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AqsTest1 {
    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        new Thread(() -> {
            reentrantLock.lock();
            try {
                System.out.println("hello");
                TestUtil.sleep(1000l);
            } finally {
                reentrantLock.unlock();
            }
        }).start();
        new Thread(() -> {
            reentrantLock.lock();
            try {
                System.out.println("wait");
                TestUtil.sleep(1000l);
            } finally {
                reentrantLock.unlock();
            }
        }).start();
        CyclicBarrier barrier = new CyclicBarrier(1);
        CountDownLatch latch = new CountDownLatch(1);
        Semaphore semaphore = new Semaphore(1);
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    }
}
