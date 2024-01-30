package com.jinx.test;

import com.jinx.test.utils.JinxLogger;
import com.jinx.test.utils.LoggerFactory;
import com.jinx.test.utils.TestUtil;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
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
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 255; j++) {
                int finalI = i;
                int finalJ = j;
                futures.add(CompletableFuture.supplyAsync(() -> {
                    found = ping("11.16." + finalI + "." + finalJ);
                    return found;
                }, executorService));
            }
        }

        ExecutorService checkFoundThread = Executors.newSingleThreadExecutor();
        checkFoundThread.execute(() -> {
            while (true) {
                if (found) {
                    log.info("找到了，正在停止其他线程...");
                    for (CompletableFuture<Boolean> future2 : futures) {
                        if (future2 != null && !future2.isDone()) {
                            future2.cancel(true);
                        }
                    }
                    log.info("找到了，已停止其他线程...");
                    break;
                }
                TestUtil.sleep(1000L);
            }
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
}
