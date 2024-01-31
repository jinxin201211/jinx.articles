package com.jinx.test.threadlocal;

import com.jinx.test.utils.JinxLogger;
import com.jinx.test.utils.LoggerFactory;
import com.jinx.test.utils.TestUtil;

import java.util.Arrays;
import java.util.Random;

public class ThreadLocalTest {
    private final static JinxLogger log = LoggerFactory.getLogger(ThreadLocalTest.class);

    public static void main(String[] args) {
//        testSynchronized();
        testThreadLocal();
    }

    private static void testSynchronized() {
        House house = new House();
        int threadNum = 10;
        int[] threadSize = new int[threadNum];
        for (int i = 0; i < threadNum; i++) {
            threadSize[i] = new Random().nextInt(10000000) + 1;
        }
        for (int i = 0; i < threadNum; i++) {
            int threadI = i;
            int sizeI = threadSize[i];
            new Thread(() -> {
                int size = new Random().nextInt(1000000) + 1;
                log.info("线程{} 需要卖出 {} 套房子", threadI, sizeI);
                for (int j = 0; j < size; j++) {
                    house.sale();
                }
            }, "thread-" + i).start();
        }

        TestUtil.sleep(300L);
        log.info("计划卖出 {} 套房子", Arrays.stream(threadSize).reduce(0, (prev, next) -> prev + next));
        log.info("已经卖出 {} 套房子", house.count);
    }

    private static void testThreadLocal() {
        House2 house = new House2();
        int threadNum = 10;
        int[] threadSize = new int[threadNum];
        for (int i = 0; i < threadNum; i++) {
            threadSize[i] = new Random().nextInt(10000000) + 1;
        }
        for (int i = 0; i < threadNum; i++) {
            int threadI = i;
            int sizeI = threadSize[i];
            new Thread(() -> {
                int size = new Random().nextInt(1000000) + 1;
                log.info("线程{} 需要卖出 {} 套房子", threadI, sizeI);
                for (int j = 0; j < size; j++) {
                    house.sale();
                }
            }, "thread-" + i).start();
        }

        TestUtil.sleep(300L);
        log.info("计划卖出 {} 套房子", Arrays.stream(threadSize).reduce(0, (prev, next) -> prev + next));
    }
}

class House {
    int count = 0;

    public synchronized void sale() {
        count++;
    }
}

class House2 {
    ThreadLocal<Integer> count = ThreadLocal.withInitial(() -> 0);

    public synchronized void sale() {
        count.set(count.get() + 1);
    }
}
