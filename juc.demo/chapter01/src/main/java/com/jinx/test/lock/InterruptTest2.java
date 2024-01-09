package com.jinx.test.lock;

import java.util.concurrent.TimeUnit;

public class InterruptTest2 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 300; i++) {
                System.out.println("线程t1运行：" + i);
            }
        }, "T1");
        t1.start();
        t1.interrupt();

        System.out.println("T1线程的中断标识：" + t1.isInterrupted());

        try {
            TimeUnit.MILLISECONDS.sleep(2L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t1.interrupt();
        System.out.println("T1线程的中断标识：" + t1.isInterrupted());

        try {
            TimeUnit.MILLISECONDS.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t1.interrupt();
        System.out.println("T1线程的中断标识：" + t1.isInterrupted());
    }
}
