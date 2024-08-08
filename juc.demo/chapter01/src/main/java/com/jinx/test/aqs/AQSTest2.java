package com.jinx.test.aqs;

import com.jinx.tool.TestUtil;

import java.util.concurrent.locks.ReentrantLock;

public class AQSTest2 {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(); //非公平锁
        // A B C办理业务，只有一个窗口
        new Thread(() -> {
            // A 是第一个客户，耗时严重，长期占用窗口
            for (int i = 0; i < 5; i++) {
                lock.lock();
                try {
                    System.out.println("Thread A entered");
//                    TestUtil.sleep(20 * 60 * 1000L);
                    TestUtil.sleep(10 * 1000L);
                } finally {
                    lock.unlock();
                }
            }
        }, "ThreadA").start();
        new Thread(() -> {
            // B 是第二个客户，但A还在办理业务，只能先去候客区等待，进入AQS队列，等待A办理完成，尝试抢占窗口
            for (int i = 0; i < 5; i++) {
                lock.lock();
                try {
                    System.out.println("Thread B entered");
                } finally {
                    lock.unlock();
                }
            }
        }, "ThreadB").start();
        new Thread(() -> {
            // C 是第三个客户，但A还在办理业务，只能先去候客区等待，进入AQS队列，等待A办理完成，尝试抢占窗口，但C前面还排着B
            for (int i = 0; i < 5; i++) {
                lock.lock();
                try {
                    System.out.println("Thread C entered");
                } finally {
                    lock.unlock();
                }
            }
        }, "ThreadC").start();
    }
}
