package com.jinx.test;

import com.jinx.tool.JinxLogger;
import com.jinx.tool.LoggerFactory;
import com.jinx.tool.TestUtil;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VPNPingTest {
    private final static JinxLogger log = LoggerFactory.getLogger(VPNPingTest.class);

    private static volatile boolean found = false;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        List<CompletableFuture<Boolean>> futures = new ArrayList<>();
        List<String> ips = readHistory();
        List<String> newIps = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            for (int j = 1; j <= 255; j++) {
                String ip = "11.16." + i + "." + j;
                if (!ips.contains(ip)) {
                    newIps.add(ip);
                }
            }
        }
        Collections.shuffle(newIps);
        ips.addAll(newIps);
        for (String ip : ips) {
            futures.add(CompletableFuture.supplyAsync(() -> {
                found = ping(ip);
                if (found) {
                    writeHistory(ip);
                }
                return found;
            }, executorService));
        }

        ExecutorService checkFoundThread = Executors.newSingleThreadExecutor();
        checkFoundThread.execute(() -> {
            while (!found) {
                TestUtil.sleep(1000L);
            }
            log.info("找到了，正在停止其他线程...");
            for (CompletableFuture<Boolean> future2 : futures) {
                if (future2 != null && !future2.isDone()) {
                    future2.cancel(true);
                }
            }
            log.info("找到了，已停止其他线程...");
        });
        checkFoundThread.shutdown();
        executorService.shutdown();
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
            log.error(ip + " 连接失败...");
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
