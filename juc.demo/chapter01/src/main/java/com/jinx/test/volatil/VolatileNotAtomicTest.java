package com.jinx.test.volatil;

import com.jinx.tool.JinxLogger;
import com.jinx.tool.LoggerFactory;
import com.jinx.tool.TestUtil;

import java.util.concurrent.atomic.AtomicInteger;

class NumberNotAtomic {
    volatile int number;

    public int getNumber() {
        return number;
    }

    public void increase() {
        number++;
    }
}

class NumberAtomic {
    AtomicInteger number = new AtomicInteger(0);

    public int getNumber() {
        return number.get();
    }

    public void increase() {
        number.incrementAndGet();
    }
}

public class VolatileNotAtomicTest {
    private static JinxLogger log = LoggerFactory.getLogger(VolatileNotAtomicTest.class);

    public static void main(String[] args) throws InterruptedException {
        NumberNotAtomic notAtomic = new NumberNotAtomic();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int i1 = 0; i1 < 1000; i1++) {
                    notAtomic.increase();
                }
            }, "notatomic" + i).start();
        }
        NumberAtomic atomic = new NumberAtomic();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int i1 = 0; i1 < 1000; i1++) {
                    atomic.increase();
                }
            }, "atomic" + i).start();
        }

        TestUtil.sleep(2000L);

        log.info(String.valueOf(notAtomic.getNumber()));
        log.info(String.valueOf(atomic.getNumber()));
    }
}
