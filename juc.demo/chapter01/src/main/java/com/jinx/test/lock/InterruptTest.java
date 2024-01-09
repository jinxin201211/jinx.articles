package com.jinx.test.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class InterruptTest {
    static volatile boolean stopped = false;

    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args) {
//        t1();
//        t2();
        t3();
    }

    private static void t1() {
        new Thread(() -> {
            while (true) {
                if (stopped) {
                    System.out.println(Thread.currentThread().getName() + "\t STOP");
                    break;
                }
                System.out.println("-------- hello volatile");
            }
        }, "T1").start();

//        try {
//            TimeUnit.MILLISECONDS.sleep(20L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        stopped = true;

        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(20L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stopped = true;
        }, "T2").start();
    }

    private static void t2() {
        new Thread(() -> {
            while (true) {
                if (atomicBoolean.get()) {
                    System.out.println(Thread.currentThread().getName() + "\t STOP");
                    break;
                }
                System.out.println("-------- hello volatile");
            }
        }, "T1").start();

        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(20L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicBoolean.set(true);
        }, "T2").start();
    }

    private static void t3() {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("[" + Thread.currentThread().getName() + "]" + "被要求中断");
                    break;
                }
                System.out.println("[" + Thread.currentThread().getName() + "]" + "RUNNING");
            }
            System.out.println("[" + Thread.currentThread().getName() + "]" + "INTERRUPTTED");
        }, "T1");
        t1.start();

        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(20L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t1.interrupt(); //给线程T1的中断标识设置为true
        }, "T2").start();
    }
}
