package com.jinx.test.utils;

public class LoggerFactory {
    public static JinxLogger getLogger(Class<?> clazz) {
        return new JinxLogger(clazz);
    }
}
