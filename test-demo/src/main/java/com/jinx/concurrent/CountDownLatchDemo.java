package com.jinx.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class CountDownLatchDemo {
    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        new Thread(() -> {
            for (int i = 0; i < 12; i++) {
                new Thread(() -> {
                    try {
                        Thread.sleep(new Random().nextInt(9999) + 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    long count = countDownLatch.getCount();
                    countDownLatch.countDown();
                    if (count != 0) {
                        log.info("线程： " + Thread.currentThread().getName() + " 组队准备，还需等待 " + countDownLatch.getCount() + " 人准备");
                    } else {
                        log.info("线程： " + Thread.currentThread().getName() + " 组队准备，房间已满不可进入");
                    }
                }).start();
            }
        }).start();


        new Thread(() -> {
            try {
                log.info("游戏房间等待玩家加入...");
                countDownLatch.await();
                log.info("游戏房间已锁定...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        log.info("等待玩家准备中...");
        countDownLatch.await();
        log.info("游戏准备中...");
    }
}
