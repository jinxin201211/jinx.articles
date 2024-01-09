package com.jinx.test.lock;

import java.util.concurrent.TimeUnit;

public class InterruptTest3 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println("[" + Thread.currentThread().getName() + "]" + "START");
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("[" + Thread.currentThread().getName() + "]" + "被要求中断");
                    break;
                }
                System.out.println("[" + Thread.currentThread().getName() + "]" + "RUNNING");
                try {
                    TimeUnit.MILLISECONDS.sleep(200L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt(); //线程阻塞时如果被interrupt，将抛出InterruptedException异常，并且中断状态被置为false
                }
            }
            System.out.println("[" + Thread.currentThread().getName() + "]" + "BLOCKING");
            try {
                TimeUnit.MILLISECONDS.sleep(20000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[" + Thread.currentThread().getName() + "]" + "FINISH");
        }, "T1");
        t1.start();

        new Thread(() -> {
//            try {
//                TimeUnit.MILLISECONDS.sleep(2000L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("[" + Thread.currentThread().getName() + "]" + "向线程T1发出中断请求");
            t1.interrupt();
        }, "T2").start();
    }
}
