package com.jinx.test.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final ThreadLocal<DateFormat> formatterDateTime = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    private static final ThreadLocal<DateFormat> formatterDate = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
//    public static SimpleDateFormat formatterDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    public static SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");

    public static Date toDate(String time, SimpleDateFormat formatter) {
        Date date = null;
        try {
            date = formatter.parse(time);
        } catch (Exception e) {
        }
        return date;
    }

    public static Date toDate(String time, DateFormat formatter) {
        Date date = null;
        try {
            date = formatter.parse(time);
        } catch (Exception e) {
        }
        return date;
    }

    public static Date toDate(String time, String format) {
        return toDate(time, new SimpleDateFormat(format));
    }

    public static Date toDate(String time) {
        return toDate(time, formatterDate.get());
    }

    public static Date toDateTime(String time) {
        return toDate(time, formatterDateTime.get());
    }

    public static String toDateString(Date date, SimpleDateFormat formatter) {
        return formatter.format(date);
    }

    public static String toDateString(Date date, DateFormat formatter) {
        return formatter.format(date);
    }

    public static String toDateString(Date date, String format) {
        return toDateString(date, new SimpleDateFormat(format));
    }

    public static String toDateString(Date date) {
        return formatterDate.get().format(date);
    }

    public static String toDateTimeString(Date date) {
        return formatterDateTime.get().format(date);
    }
}
