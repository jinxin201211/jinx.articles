package com.jinx.tool;

public class LoggerFactory {
    public static JinxLogger getLogger(Class<?> clazz) {
        return new JinxLogger(clazz);
    }
}
