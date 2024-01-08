package com.jinx.test.lock;

import java.util.concurrent.locks.ReentrantLock;

class Ticket {
    private int number = 50;
    ReentrantLock lock = new ReentrantLock(true);

    public void sale() {
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "售出第" + number-- + "张票\t还剩下" + number + "张票");
            }
        } finally {
            lock.unlock();
        }
    }
}

public class SaleTicketTest {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(() -> {
            for (int i = 0; i < 55; i++) ticket.sale();
        }, "Conductor A").start();
        new Thread(() -> {
            for (int i = 0; i < 55; i++) ticket.sale();
        }, "Conductor B").start();
        new Thread(() -> {
            for (int i = 0; i < 55; i++) ticket.sale();
        }, "Conductor C").start();
    }
}
