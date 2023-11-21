package com.jinx.test.string;

import org.junit.Test;

public class StringTest4 {
    @Test
    public void test1() {
        String str1 = "a" + "b" + "c";
        String str2 = "abc";

        System.out.println(str1 == str2); //true
        System.out.println(str1.equals(str2)); //true
    }

    @Test
    public void test2() {
        String str1 = "JavaEE";
        String str2 = "Hadoop";

        String str3 = "JavaEEHadoop";
        String str4 = "JavaEE" + "Hadoop";
        String str5 = str1 + str2;
        String str6 = str1 + "Hadoop";
        String str7 = "JavaEE" + str2;

        System.out.println(str3 == str4); //true
        System.out.println(str3 == str5); //false
        System.out.println(str3 == str6); //false
        System.out.println(str3 == str7); //false
        System.out.println(str5 == str6); //false
        System.out.println(str5 == str7); //false
        System.out.println(str6 == str7); //false

        String str8 = str7.intern();
        System.out.println(str3 == str8); //true

        System.out.println(str3.hashCode());
        System.out.println(str4.hashCode());
        System.out.println(str5.hashCode());
        System.out.println(str6.hashCode());
        System.out.println(str7.hashCode());
        System.out.println(str8.hashCode());
    }

    @Test
    public void test3() {
        String str1 = "a";
        String str2 = "b";
        String str3 = "ab";
        String str4 = str1 + str2;
        System.out.println(str3 == str4);
        System.out.println(str3 == "ab");
        System.out.println(str4 == "ab");
    }
}
