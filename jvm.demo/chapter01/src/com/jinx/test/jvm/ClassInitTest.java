package com.jinx.test.jvm;

public class ClassInitTest {
    public static int num1 = 1;

    static {
        num1 = 2;
        num2 = 4;
        num3 = 6;
    }

    public static int num2 = 3;
    public static int num3;

    public static void main(String[] args) {
        System.out.println(num1);
        System.out.println(num2);
        System.out.println(num3);
    }
}
