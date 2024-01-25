package com.jinx.test.atomic;

import com.jinx.test.utils.JinxLogger;
import com.jinx.test.utils.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicFieldUpdaterTest {
    private final static JinxLogger log = LoggerFactory.getLogger(AtomicFieldUpdaterTest.class);

    public static void main(String[] args) {
        BankAcocunt bankAcocunt = new BankAcocunt();
        CountDownLatch count = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
//                    bankAcocunt.add();
                    bankAcocunt.transferAccount(bankAcocunt);
                }
                count.countDown();
            }, "Thread" + i).start();
        }

        try {
            count.await();
            log.info(String.valueOf(bankAcocunt.amount));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class BankAcocunt {
    private String account;

    public volatile int amount = 0;

    public void add() {
        amount++;
    }

    AtomicIntegerFieldUpdater<BankAcocunt> fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(BankAcocunt.class, "amount");

    public void transferAccount(BankAcocunt bankAcocunt) {
        fieldUpdater.getAndIncrement(bankAcocunt);
    }
}