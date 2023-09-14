package com.jinx.test.jvm;

public class StackFrameTest {
    public static void main(String[] args) {
        method1();
    }

    private static void method1() {
        System.out.println("method1()开始执行...");
        method2();
        System.out.println("method1()执行结束...");
    }

    private static int method2() {
        System.out.println("method2()开始执行...");
        int i = 1;
        method3();
        System.out.println("method2()执行结束...");
        return i;
    }

    private static double method3() {
        System.out.println("method3()开始执行...");
        double i = 2.0;
        System.out.println("method3()执行结束...");
        return i;
    }
}
