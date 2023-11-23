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
        System.out.println(str3 == str4); //false
        System.out.println(str3 == "ab"); //true
        System.out.println(str4 == "ab"); //false

        final String str6 = "a";
        String str7 = str6 + "b";
        System.out.println(str3 == str7); //true
    }

    @Test
    public void test4() {
        int loopCount = 100000;
        long begin1 = System.currentTimeMillis();
        concatMethod1(loopCount);
        long end1 = System.currentTimeMillis();
        System.out.println(loopCount + "耗时：" + (end1 - begin1) + "ms");

        long begin2 = System.currentTimeMillis();
        concatMethod2(loopCount);
        long end2 = System.currentTimeMillis();
        System.out.println(loopCount + "耗时：" + (end2 - begin2) + "ms");
    }

    public void concatMethod1(int loopCount) {
        String str = "";
        for (int i = 0; i < loopCount; i++) {
            str += "a";
        }
    }

    public void concatMethod2(int loopCount) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < loopCount; i++) {
            str.append("a");
        }
    }

    @Test
    public void test5() {
        String str1 = "a";
        String str2 = "b";
        String str3 = "ab";
        String str4 = (str1 + str2).intern();
        System.out.println(str3 == str4); //true
        System.out.println(str3 == "ab"); //true
        System.out.println(str4 == "ab"); //true
        String str5 = str1 + str2;
        str5.intern();
        System.out.println(str3 == str5); //false

        String str6 = new String(str1 + str2);
        System.out.println(str3 == str6); //false
    }
}
