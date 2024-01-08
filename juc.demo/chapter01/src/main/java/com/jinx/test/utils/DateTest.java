package com.jinx.test.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {
    public static void main(String[] args) {
        Date date = new Date();

        System.out.println(DateUtil.toDateString(date));
        System.out.println(DateUtil.toDateTimeString(date));
        System.out.println(DateUtil.toDateString(date, "yyyy-MM-dd"));
        System.out.println(DateUtil.toDateString(date, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
    }
}
