package com.jinx.test.string;

public class StringTest {
    public static void main(String[] args) {
//        String s1 = "1";
//        System.out.println(toString(s1));
//        s1 = "2";
//        System.out.println(toString(s1));
//        s1 = "3";
//        System.out.println(toString(s1));
//
//        Integer i1 = 1111;
//        System.out.println(toString(i1));
//        i1 = 2222;
//        System.out.println(toString(i1));
//        i1 = 3333;
//        System.out.println(toString(i1));

        String s11 = "abc11111111";
        String s12 = "abc11111111";
        System.out.println(s11 == s12);
        System.out.println(s11.equals(s12));
        s12 = new String(s12);
        System.out.println(s11 == s12);
        System.out.println(s11.equals(s12));

        try {
            Thread.sleep(1000000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String toString(String str) {
        return str.getClass().getName() + "@" + Integer.toHexString(str.hashCode());
    }

    private static String toString(Integer itg) {
        return itg.getClass().getName() + "@" + Integer.toHexString(itg.hashCode());
    }
}
