package com.jinx.test.cas;

import com.jinx.test.utils.JinxLogger;
import com.jinx.test.utils.LoggerFactory;
import com.jinx.test.utils.TestUtil;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStamppedReferenceTest {
    private final static JinxLogger log = LoggerFactory.getLogger(AtomicStamppedReferenceTest.class);

    static AtomicInteger atomicInteger = new AtomicInteger(100);
    static AtomicStampedReference<Integer> atomicStampedInteger = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {
//        testNoStampped();
        testStampped();
    }

    private static void testNoStampped() {
        new Thread(() -> {
            atomicInteger.compareAndSet(100, 101);
            TestUtil.sleep(10L);
            atomicInteger.compareAndSet(101, 100);
        }, "T1").start();
        new Thread(() -> {
            TestUtil.sleep(200L);
            log.info(atomicInteger.compareAndSet(100, 2024) + "\t" + atomicInteger.get());
        }, "T2").start();
    }

    private static void testStampped() {
        new Thread(() -> {
            log.info("首次版本号：" + atomicStampedInteger.getStamp());

            TestUtil.sleep(500L);

            atomicStampedInteger.compareAndSet(100, 101, atomicStampedInteger.getStamp(), atomicStampedInteger.getStamp() + 1);
            log.info("第1次更改后版本号：" + atomicStampedInteger.getStamp());

            atomicStampedInteger.compareAndSet(101, 100, atomicStampedInteger.getStamp(), atomicStampedInteger.getStamp() + 1);
            log.info("第2次更改后版本号：" + atomicStampedInteger.getStamp());
        }, "T3").start();
        new Thread(() -> {
            int stamp = atomicStampedInteger.getStamp();
            log.info("首次版本号：" + stamp);

            TestUtil.sleep(1000L);

            log.info(atomicStampedInteger.compareAndSet(100, 2024, stamp, stamp + 1) + "\t" + atomicInteger.get());
        }, "T4").start();
    }
}
