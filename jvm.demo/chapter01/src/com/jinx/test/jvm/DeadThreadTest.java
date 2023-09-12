package com.jinx.test.jvm;

public class DeadThreadTest {
    public static void main(String[] args) {
        Runnable r = () -> {
            System.out.println(Thread.currentThread().getName() + " 开始");
            DeadThread dt = new DeadThread();
            System.out.println(Thread.currentThread().getName() + " 结束");
        };

        Thread t1 = new Thread(r, "Thread 1");
        Thread t2 = new Thread(r, "Thread 2");
        Thread t3 = new Thread(r, "Thread 3");

        t1.start();
        t2.start();
        t3.start();
    }
}

class DeadThread {
    static {
        if (true) {
            System.out.println(Thread.currentThread().getName() + " 初始化当前类");
//            while (true) {
//
//            }
        }
    }
}
