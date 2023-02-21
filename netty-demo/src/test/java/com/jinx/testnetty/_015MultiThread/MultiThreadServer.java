package com.jinx.testnetty._015MultiThread;

import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;

public class MultiThreadServer {
    public static void main(String[] args) {
        Thread.currentThread().setName("boss");
        ServerSocketChannel bossChannel = ServerSocketChannel.open();
        bossChannel.configureBlocking(false);

        SelectionKey
    }
}
