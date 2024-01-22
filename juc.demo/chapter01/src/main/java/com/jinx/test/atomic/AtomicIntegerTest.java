package com.jinx.test.atomic;

import com.jinx.test.utils.JinxLogger;
import com.jinx.test.utils.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {
    private final static JinxLogger log = LoggerFactory.getLogger(AtomicIntegerTest.class);

    public final static int SIZE = 50;

    public static void main(String[] args) {
        MyNumber myNumber = new MyNumber();
        CountDownLatch count = new CountDownLatch(SIZE);
        for (int i = 0; i < SIZE; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myNumber.addPlusPlus();
                }
                count.countDown();
            }, "Thread" + i).start();
        }

//        TestUtil.sleep(2000L);

        try {
            count.await();
            log.info(myNumber.atomicInteger.get() + "");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyNumber {
    AtomicInteger atomicInteger = new AtomicInteger();

    public void addPlusPlus() {
        atomicInteger.getAndIncrement();
    }
}

