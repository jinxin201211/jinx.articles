package com.jinx.test.atomic;

import com.jinx.test.utils.JinxLogger;
import com.jinx.test.utils.LoggerFactory;
import com.jinx.test.utils.TestUtil;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class AtomicMarkableReferenceTest {
    private final static JinxLogger log = LoggerFactory.getLogger(AtomicMarkableReferenceTest.class);

    private static final AtomicMarkableReference<Integer> atomicMarkableReference = new AtomicMarkableReference<>(100, false);

    public static void main(String[] args) {
        new Thread(() -> {
            boolean marked = atomicMarkableReference.isMarked();
            log.info("默认标识：" + marked);
            TestUtil.sleep(1L);
            boolean b = atomicMarkableReference.compareAndSet(100, 1000, marked, !marked);
            log.info("操作成功，" + b + " 最终值：" + atomicMarkableReference.getReference() + " " + atomicMarkableReference.isMarked());
        }, "T1").start();
        new Thread(() -> {
            boolean marked = atomicMarkableReference.isMarked();
            log.info("默认标识：" + marked);
            TestUtil.sleep(2L);
            boolean b = atomicMarkableReference.compareAndSet(100, 2000, marked, !marked);
            log.info("操作成功，" + b + " 最终值：" + atomicMarkableReference.getReference() + " " + atomicMarkableReference.isMarked());
        }, "T2").start();
    }
}
