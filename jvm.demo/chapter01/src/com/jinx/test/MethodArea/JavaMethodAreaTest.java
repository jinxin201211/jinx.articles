package com.jinx.test.MethodArea;

public class JavaMethodAreaTest {
    int num = 0;

    private void test1() {
        int num1 = 0;
        System.out.println(num1);
    }

    public static void test2() {
        int num2 = 0;
        System.out.println(num2);
    }

    public int test3(int n, int m) throws ArithmeticException {
        int num1 = 0;
        System.out.println(n / num1);
        System.out.println(m / num1);
        return n + m;
    }

    public static void main(String[] args) {
        new JavaMethodAreaTest().test1();
        test2();
        new JavaMethodAreaTest().test3(1, 10);
    }
}
