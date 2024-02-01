package com.jinx.test.cas;

import com.jinx.tool.JinxLogger;
import com.jinx.tool.LoggerFactory;
import com.jinx.tool.TestUtil;

import java.util.concurrent.atomic.AtomicReference;

public class SpinLockTest {
    private final static JinxLogger log = LoggerFactory.getLogger(SpinLockTest.class);

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void lock() {
        log.info("come in.");
        Thread thread = Thread.currentThread();
        while (!atomicReference.compareAndSet(null, thread)) ;
    }

    public void unlock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        log.info("task over, unlock.");
    }

    public static void main(String[] args) {
        SpinLockTest spinLockTest = new SpinLockTest();

        new Thread(() -> {
            spinLockTest.lock();
            TestUtil.sleep(1000L);
            spinLockTest.unlock();
        }, "A").start();

        TestUtil.sleep(500L);

        new Thread(() -> {
            spinLockTest.lock();
            spinLockTest.unlock();
        }, "B").start();

    }
}
