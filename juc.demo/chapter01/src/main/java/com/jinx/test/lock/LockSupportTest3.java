package com.jinx.test.lock;

import com.jinx.test.utils.JinxLogger;
import com.jinx.test.utils.LoggerFactory;
import com.jinx.test.utils.TestUtil;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class LockSupportTest3 {
    private static final JinxLogger log = LoggerFactory.getLogger(LockSupportTest3.class);

    public static void main(String[] args) {
        System.out.println("\033[030m30m" +
                "\033[031m31m" +
                "\033[032m32m" +
                "\033[033m33m" +
                "\033[034m34m" +
                "\033[035m35m" +
                "\033[036m36m" +
                "\033[037m37m" +
                "\033[038m38m" +
                "\033[039m39m");
//        test1();
//        test2();
//        test3();
        test4();
    }

    private static void test1() {
        Thread t1 = new Thread(() -> {
            System.out.println("[" + Thread.currentThread().getName() + "]" + "ENTER");
            LockSupport.park();
            System.out.println("[" + Thread.currentThread().getName() + "]" + "WAKEUP");
        }, "T1");
        t1.start();

        try {
            TimeUnit.MILLISECONDS.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("[" + Thread.currentThread().getName() + "]" + "ENTER");
            LockSupport.unpark(t1);
            System.out.println("[" + Thread.currentThread().getName() + "]" + "NOTIFY");
        }, "T2").start();
    }

    private static void test2() {
        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[" + Thread.currentThread().getName() + "]" + "ENTER");
            LockSupport.park();
            System.out.println("[" + Thread.currentThread().getName() + "]" + "WAKEUP");
        }, "T1");
        t1.start();

        new Thread(() -> {
            System.out.println("[" + Thread.currentThread().getName() + "]" + "ENTER");
            LockSupport.unpark(t1);
            System.out.println("[" + Thread.currentThread().getName() + "]" + "NOTIFY");
        }, "T2").start();
    }

    private static void test3() {
        Thread t1 = new Thread(() -> {
            System.out.println("[" + Thread.currentThread().getName() + "]" + "ENTER");
            LockSupport.park();
            LockSupport.park();
            System.out.println("[" + Thread.currentThread().getName() + "]" + "WAKEUP");
        }, "T1");
        t1.start();

        try {
            TimeUnit.MILLISECONDS.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("[" + Thread.currentThread().getName() + "]" + "ENTER");
            LockSupport.unpark(t1);
            LockSupport.unpark(t1);
            System.out.println("[" + Thread.currentThread().getName() + "]" + "NOTIFY");
        }, "T2").start();
    }

    private static void test4() {
        Thread t1 = new Thread(() -> {
            TestUtil.sleep();
            log.info("ENTER");
            LockSupport.park();
            LockSupport.park();
            log.info("WAKEUP");
        }, "T1");
        t1.start();

        new Thread(() -> {
            log.info("ENTER");
            LockSupport.unpark(t1);
            LockSupport.unpark(t1);
            log.info("NOTIFY");
        }, "T2").start();
    }
}
