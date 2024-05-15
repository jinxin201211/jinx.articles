package com.jinx.test;

import com.jinx.tool.JinxLogger;
import com.jinx.tool.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class VPNPingTest {
    private final static JinxLogger log = LoggerFactory.getLogger(VPNPingTest.class);

    private static final AtomicBoolean found = new AtomicBoolean(false);

    private static final int CONCURRENT_NUMBER = 10;

    private static final short MIN_HOST = 0;

    private static final short MAX_HOST = 5;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(CONCURRENT_NUMBER);
        List<String> ips = readHistory();
        Collections.reverse(ips);
        List<String> newIps = new ArrayList<>();
        for (int i = MIN_HOST; i <= MAX_HOST; i++) {
            for (int j = 1; j <= 255; j++) {
                String ip = "11.16." + i + "." + j;
                if (!ips.contains(ip) && !"11.16.1.218".equals(ip)) {
                    newIps.add(ip);
                }
            }
        }
        Collections.shuffle(newIps);
        ips.addAll(newIps);
        for (String ip : ips) {
            CompletableFuture.supplyAsync(() -> {
                if (!found.get()) {
                    boolean isFound = ping(ip);
                    if (isFound) {
                        found.set(true);
                        log.info("找到了...");
                        writeHistory(ip);
                    }
                }
                return found;
            }, executorService);
        }
        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) { // 等待一段时间确保任务完成
                executorService.shutdownNow(); // 强制关闭
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 恢复中断状态
            executorService.shutdownNow(); // 强制关闭
        }
    }

    private static boolean ping(String ip) {
        int port = 81;
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), 1000);
            log.info(ip + " 连接成功...");
            socket.close();
            return true;
        } catch (Exception e) {
            log.warn(ip + " 连接失败...");
            return false;
        }
    }

    private static final String HISTORY_FILE_PATH = "D:\\Repository\\jinx.articles\\juc.demo\\VPNPingTool\\src\\main\\resources\\history.txt";

    private static List<String> readHistory() {
        try {
            List<String> ips = new ArrayList<>();
            FileReader fileReader = new FileReader(HISTORY_FILE_PATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!"".equals(line)) {
                    ips.add(line);
                }
            }
            bufferedReader.close();
            fileReader.close();
            return ips;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static void writeHistory(String ip) {
        List<String> ips = readHistory();
        if (!ips.contains(ip)) {
            try {
                FileWriter fileWriter = new FileWriter(HISTORY_FILE_PATH, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(ip);
                bufferedWriter.newLine();
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
