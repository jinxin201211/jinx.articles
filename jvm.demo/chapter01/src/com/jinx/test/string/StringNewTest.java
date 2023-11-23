package com.jinx.test.string;

public class StringNewTest {
    public static void main(String[] args) {
//        String str = new String("abc"); //1.new String 2.字符串常量池"abc"
        String str = new String("a") + new String("b"); // 1.new StringBuilder
        // 2.new String
        // 3.字符串常量池"a"
        // 4.new String
        // 5.字符串常量池"b"
        // 6.StringBuilder.toString()中new String("ab")
        // 字符串常量池中不存在"ab"

        String s1 = new String("11"); //会在字符串常量池中创建"11"，s1指向的不是常量池中的"11"
        s1.intern();//字符串常量池中已有"11"，不再创建，仅返回已存在的那个"11"，但不会对s1自身造成任何修改
        String s2 = "11";//s2指向常量池中的"11"
        System.out.println(s1 == s2); //false

        String s3 = new String("a") + new String("b"); //不会在字符串常量池中创建"ab"
        s3.intern(); //在字符串常量池中生成"ab"(堆中已存在"ab"，字符串常量池中不会再创建"ab"，而是创建一个引用指向堆中new的"ab"，与s3指向同一个"ab")
        String s4 = "ab"; //s4指向s3.intern()在常量池中生成的"ab"
        System.out.println(s3 == s4); //true

        String s5 = new String("a") + new String("b");
        String s6 = "ab";
        s5.intern();
        System.out.println(s5 == s6); //false
    }
}
