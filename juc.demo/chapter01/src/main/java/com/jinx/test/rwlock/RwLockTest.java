package com.jinx.test.rwlock;

import com.jinx.tool.TestUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RwLockTest {
    public static void main(String[] args) {
        RwLockResrouce resrouce = new RwLockResrouce();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                resrouce.write(finalI + "", finalI + "");
            }, i + "").start();
        }
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                resrouce.read(finalI + "");
            }, i + "").start();
        }
        TestUtil.sleep(1000);
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            new Thread(() -> {
                resrouce.write(finalI + "", finalI + "");
            }, "new " + i + "").start();
        }
    }
}

class RwLockResrouce {
    private Map<String, String> resource = new HashMap<>();

    private Lock lock = new ReentrantLock();

    private ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();

    public void write(String key, String value) {
//        lock.lock();
        rwlock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t正在写入");
            resource.put(key, value);
            TestUtil.sleep(500);
            System.out.println(Thread.currentThread().getName() + "\t写入完毕");
        } finally {
//            lock.unlock();
            rwlock.writeLock().unlock();
        }
    }

    public void read(String key) {
//        lock.lock();
        rwlock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t正在读取");
            String s = resource.get(key);
            TestUtil.sleep(2000);
            System.out.println(Thread.currentThread().getName() + "\t读取完毕\t" + s);
        } finally {
//            lock.unlock();
            rwlock.readLock().unlock();
        }
    }
}
