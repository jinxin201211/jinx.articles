package com.jinx.test.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Logger {
    private final static int CLASS_NAME_LENGTH = 40;

    private final static int THREAD_NAME_LENGTH = 15;

    private final static Map<LogLevel, String> LEVEL_COLOR = new HashMap<>();

    private final static Map<LogLevel, String> LEVEL_TEXT = new HashMap<>();

    private final static Map<String, Function<String, String>> CONSOLE_OUTPUT = new HashMap<>();

    static {
        LEVEL_COLOR.put(LogLevel.INFO, ConsoleColor.GREEN.getValue());
        LEVEL_COLOR.put(LogLevel.DEBUG, ConsoleColor.GREEN.getValue());
        LEVEL_COLOR.put(LogLevel.WARN, ConsoleColor.YELLOW.getValue());
        LEVEL_COLOR.put(LogLevel.ERROR, ConsoleColor.RED.getValue());

        LEVEL_TEXT.put(LogLevel.INFO, " INFO");
        LEVEL_TEXT.put(LogLevel.DEBUG, "DEBUG");
        LEVEL_TEXT.put(LogLevel.WARN, " WARN");
        LEVEL_TEXT.put(LogLevel.ERROR, "ERROR");

        CONSOLE_OUTPUT.put("TIME", (s) -> partial(ConsoleColor.BLUE.getValue(), s));
        CONSOLE_OUTPUT.put("PID", (s) -> partial(ConsoleColor.PURPLE.getValue(), s));
        CONSOLE_OUTPUT.put("THREAD", (s) -> partial(ConsoleColor.GREEN.getValue(), s));
        CONSOLE_OUTPUT.put("CLASS", (s) -> partial(ConsoleColor.CYAN.getValue(), s));
    }

    private static String partial(String color, String message) {
        return color + message + ConsoleColor.WHITE.getValue();
    }

    private final Class<?> clazz;

    public Logger(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Logger() {
        this.clazz = this.getClass();
    }

    private void log(String message, LogLevel logLevel) {
        System.out.printf("%s %s %s --- [%s] %s : %s\n",
                CONSOLE_OUTPUT.get("TIME").apply(DateUtil.toDateString(new Date(), "YYYY-MM-DD HH:mm:ss.SSS")),
                partial(LEVEL_COLOR.get(logLevel), LEVEL_TEXT.get(logLevel)),
                CONSOLE_OUTPUT.get("PID").apply(getProgressID()),
                CONSOLE_OUTPUT.get("THREAD").apply(formatThreadName(Thread.currentThread().getName())),
                CONSOLE_OUTPUT.get("CLASS").apply(formatClassName(clazz.getName())),
                message);
    }

    private String getProgressID() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String jvmName = runtime.getName();
        return jvmName.substring(0, jvmName.indexOf('@'));
    }

    private String formatClassName(String className) {
        if (className.length() <= CLASS_NAME_LENGTH) {
            StringBuilder classNameBuilder = new StringBuilder(className);
            for (int i = 0; i < CLASS_NAME_LENGTH - classNameBuilder.length(); i++) {
                classNameBuilder.append(" ");
            }
            className = classNameBuilder.toString();
            return className;
        } else {
            String[] packagesAndClasses = className.split("\\.");
            StringBuilder formatted = new StringBuilder();
            for (int i = 0; i < packagesAndClasses.length - 1; i++) {
                packagesAndClasses[i] = String.valueOf(packagesAndClasses[i].charAt(0));
                formatted = new StringBuilder(String.join(".", packagesAndClasses));
                if (formatted.length() <= CLASS_NAME_LENGTH) {
                    break;
                }
            }
            if (formatted.length() > CLASS_NAME_LENGTH) {
                formatted = new StringBuilder(formatted.substring(formatted.length() - CLASS_NAME_LENGTH, formatted.length()));
            } else {
                for (int i = 0; i < CLASS_NAME_LENGTH - formatted.length(); i++) {
                    formatted.append(" ");
                }
            }
            return formatted.toString();
        }
    }

    private String formatThreadName(String threadName) {
        if (threadName.length() > THREAD_NAME_LENGTH) {
            return "..." + threadName.substring(threadName.length() - THREAD_NAME_LENGTH + 3, threadName.length());
        } else {
            StringBuilder formatted = new StringBuilder();
            for (int i = 0; i < THREAD_NAME_LENGTH - threadName.length(); i++) {
                formatted.append(" ");
            }
            formatted.append(threadName);
            return formatted.toString();
        }
    }

    public void info(String message) {
        log(message, LogLevel.INFO);
    }

    public void debug(String message) {
        log(message, LogLevel.DEBUG);
    }

    public void warn(String message) {
        log(message, LogLevel.WARN);
    }

    public void error(String message) {
        log(message, LogLevel.ERROR);
    }
}

enum LogLevel {
    INFO,
    DEBUG,
    WARN,
    ERROR
}

enum ConsoleColor {
    BLACK("\033[030m"),
    RED("\033[031m"),
    GREEN("\033[032m"),
    YELLOW("\033[033m"),
    BLUE("\033[034m"),
    PURPLE("\033[035m"),
    CYAN("\033[036m"),
    GRAY("\033[037m"),
    WHITE("\033[038m");

    private String value;

    ConsoleColor(String s) {
        value = s;
    }

    public String getValue() {
        return value;
    }
}