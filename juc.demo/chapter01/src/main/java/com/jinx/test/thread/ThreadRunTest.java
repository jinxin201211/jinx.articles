package com.jinx.test.thread;

public class ThreadRunTest {
    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ": Hello Thread!");
        }, "task-1").start();
    }
}
