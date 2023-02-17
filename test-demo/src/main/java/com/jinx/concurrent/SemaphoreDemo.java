package com.jinx.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.Semaphore;

@Slf4j
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        log.debug("初始化 {} 个银行柜台窗口", 5);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    log.debug("用户 {} 排队等待", Thread.currentThread().getName());
                    semaphore.acquire(1);
                    log.debug("用户 {} 占用窗口，开始办理业务", Thread.currentThread().getName());
                    Thread.sleep(new Random().nextInt(9999) + 5000);
                    semaphore.release();
                    log.debug("用户 {} 离开窗口", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
