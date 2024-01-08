package com.jinx.test.lock;

import java.util.concurrent.TimeUnit;

class Phone {
    public synchronized void sendEmail1() {
        System.out.println("------send email");
    }

    public synchronized void sendEmail2() {
        try {
            TimeUnit.MILLISECONDS.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("------send email");
    }

    public synchronized void sendSms1() {
        System.out.println("------send sms");
    }

    public static synchronized void sendEmail3() {
        try {
            TimeUnit.MILLISECONDS.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("------send email");
    }

    public static synchronized void sendSms3() {
        System.out.println("------send sms");
    }

    public static synchronized void sendEmail4() {
        try {
            TimeUnit.MILLISECONDS.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("------send email");
    }

    public synchronized void sendSms4() {
        System.out.println("------send sms");
    }

    public void hello() {
        System.out.println("------hello");
    }
}

public class LockTest {
    public static void main(String[] args) {
//        test1(); //一个对象内多个synchronized方法
//        test2();
//        test3();
//        test4();
//        test5();
//        test6();
//        test7();
        test8();
    }

    private static void test1() {
        Phone phone = new Phone();
        new Thread(phone::sendEmail1, "Thread A").start();

        try {
            TimeUnit.MILLISECONDS.sleep(200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Phone phone2 = new Phone();
        new Thread(phone2::sendSms1, "Thread B").start();
    }

    private static void test2() {
        Phone phone = new Phone();
        new Thread(phone::sendEmail2, "Thread A").start();

        try {
            TimeUnit.MILLISECONDS.sleep(200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(phone::sendSms1, "Thread B").start();
    }

    private static void test3() {
        Phone phone = new Phone();
        new Thread(phone::sendEmail2, "Thread A").start();

        try {
            TimeUnit.MILLISECONDS.sleep(200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(phone::hello, "Thread B").start();
    }

    private static void test4() {
        Phone phone = new Phone();
        new Thread(phone::sendEmail2, "Thread A").start();

        try {
            TimeUnit.MILLISECONDS.sleep(200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Phone phone2 = new Phone();
        new Thread(phone2::sendSms1, "Thread B").start();
    }

    private static void test5() {
        Phone phone = new Phone();
        new Thread(() -> {
            phone.sendEmail3();
        }, "Thread A").start();

        try {
            TimeUnit.MILLISECONDS.sleep(200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            phone.sendSms3();
        }, "Thread B").start();
    }

    private static void test6() {
        Phone phone = new Phone();
        new Thread(() -> {
            phone.sendEmail3();
        }, "Thread A").start();

        try {
            TimeUnit.MILLISECONDS.sleep(200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Phone phone2 = new Phone();
        new Thread(() -> {
            phone2.sendSms3();
        }, "Thread B").start();
    }

    private static void test7() {
        Phone phone = new Phone();
        new Thread(() -> {
            phone.sendEmail4();
        }, "Thread A").start();

        try {
            TimeUnit.MILLISECONDS.sleep(200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            phone.sendSms4();
        }, "Thread B").start();
    }

    private static void test8() {
        Phone phone = new Phone();
        new Thread(() -> {
            phone.sendEmail4();
        }, "Thread A").start();

        try {
            TimeUnit.MILLISECONDS.sleep(200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Phone phone2 = new Phone();
        new Thread(() -> {
            phone2.sendSms4();
        }, "Thread B").start();
    }
}
