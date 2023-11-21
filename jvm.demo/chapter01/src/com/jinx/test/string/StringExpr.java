package com.jinx.test.string;

public class StringExpr {
    static String str = new String("Hello");
    static char[] chrs = new char[]{'H', 'E', 'L', 'L', 'O'};

    private static void change(String s, char[] cs) {
        s = "World";
        cs[0] = 'W';
    }

    public static void main(String[] args) {
        System.out.println(str);
        System.out.println(chrs);
        change(str, chrs);
        System.out.println(str);
        System.out.println(chrs);

        System.out.println("🀎".length());
        System.out.println("🀎".charAt(0));
        System.out.println("🀎".charAt(1));
    }
}
