package com.jinx.test.jvm;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AlternativePrintTest {
    private static CountDownLatch oddCountDownLatch = new CountDownLatch(0);
    private static CountDownLatch evenCountDownLatch = new CountDownLatch(1);
    private static int num = 1;

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 60, TimeUnit.SECONDS, new ArrayBlockingQueue(2 * 2 * 10));

        executor.execute(() -> {
            while (num < 100) {
                try {
                    oddCountDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ": " + num);
                num++;
                oddCountDownLatch = new CountDownLatch(1);
                evenCountDownLatch.countDown();
            }
        });

        executor.execute(() -> {
            while (num < 100) {
                try {
                    evenCountDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ": " + num);
                num++;
                evenCountDownLatch = new CountDownLatch(1);
                oddCountDownLatch.countDown();
            }
        });
    }
}
