package com.jinx.test.string;

public class StringTest3 {
    public static void main(String[] args) {
        int i = 2;
        Object obj = new Object();
        StringTest3 stringTest3 = new StringTest3();
        stringTest3.foo(obj);
    }

    private void foo(Object obj) {
        String str = obj.toString();
        System.out.println(str);
    }
}
