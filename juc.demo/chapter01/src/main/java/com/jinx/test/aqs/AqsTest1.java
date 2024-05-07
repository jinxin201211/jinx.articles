package com.jinx.test.aqs;

import com.jinx.tool.TestUtil;

import java.util.concurrent.locks.ReentrantLock;

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
    }
}
