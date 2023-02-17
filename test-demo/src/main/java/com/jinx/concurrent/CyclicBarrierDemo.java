package com.jinx.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

@Slf4j
public class CyclicBarrierDemo {
    public static void main(String[] args) throws Exception {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        for (int i = 0; i < 12; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(new Random().nextInt(9999) + 1000);
                    log.info("线程： " + Thread.currentThread().getName() + " 组队准备，当前 " + (cyclicBarrier.getNumberWaiting() + 1) + " 人已进入");
                    cyclicBarrier.await();
                    log.info("线程： " + Thread.currentThread().getName() + " 开始组队游戏");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
