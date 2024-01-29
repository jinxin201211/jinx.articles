package com.jinx.test.atomic;

import com.jinx.test.utils.JinxLogger;
import com.jinx.test.utils.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

public class LikeCountTest {
    private final static JinxLogger log = LoggerFactory.getLogger(LikeCountTest.class);

    private static final int THREAD_COUNT = 50;

    private static final int LIKE_COUNT = 1000000;

    public static void main(String[] args) throws InterruptedException {
        likeSynchronized();
        likeAtomic();
        likeAdder();
        likeAccumulator();
    }

    private static void likeSynchronized() throws InterruptedException {
        LikeCount likeCount = new LikeCount();
        CountDownLatch latch1 = new CountDownLatch(THREAD_COUNT);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                for (int j = 0; j < LIKE_COUNT; j++) {
                    likeCount.like();
                }
                latch1.countDown();
            }, "thread" + (i + 1)).start();
        }
        latch1.await();
        long finishTime = System.currentTimeMillis();
        log.info("第一种方法：" + (finishTime - startTime) + "，结果：" + likeCount.count);
    }

    private static void likeAtomic() throws InterruptedException {
        LikeCount likeCount = new LikeCount();
        CountDownLatch latch2 = new CountDownLatch(THREAD_COUNT);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                for (int j = 0; j < LIKE_COUNT; j++) {
                    likeCount.likeAtomic();
                }
                latch2.countDown();
            }, "thread" + (i + 1)).start();
        }
        latch2.await();
        long finishTime = System.currentTimeMillis();
        log.info("第二种方法：" + (finishTime - startTime) + "，结果：" + likeCount.aCount.get());
    }

    private static void likeAdder() throws InterruptedException {
        LikeCount likeCount = new LikeCount();
        CountDownLatch latch3 = new CountDownLatch(THREAD_COUNT);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                for (int j = 0; j < LIKE_COUNT; j++) {
                    likeCount.likeAdder();
                }
                latch3.countDown();
            }, "thread" + (i + 1)).start();
        }
        latch3.await();
        long finishTime = System.currentTimeMillis();
        log.info("第三种方法：" + (finishTime - startTime) + "，结果：" + likeCount.lCount.longValue());
    }

    private static void likeAccumulator() throws InterruptedException {
        LikeCount likeCount = new LikeCount();
        CountDownLatch latch4 = new CountDownLatch(THREAD_COUNT);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                for (int j = 0; j < LIKE_COUNT; j++) {
                    likeCount.likeAccumulator();
                }
                latch4.countDown();
            }, "thread" + (i + 1)).start();
        }
        latch4.await();
        long finishTime = System.currentTimeMillis();
        log.info("第四种方法：" + (finishTime - startTime) + "，结果：" + likeCount.laCount.longValue());
    }
}

class LikeCount {
    int count = 0;

    public synchronized void like() {
        count++;
    }

    AtomicLong aCount = new AtomicLong(0);

    public void likeAtomic() {
        aCount.getAndIncrement();
    }

    LongAdder lCount = new LongAdder();

    public void likeAdder() {
        lCount.increment();
    }

    LongAccumulator laCount = new LongAccumulator((x, y) -> x + y, 0l);

    public void likeAccumulator() {
        laCount.accumulate(1);
    }
}
