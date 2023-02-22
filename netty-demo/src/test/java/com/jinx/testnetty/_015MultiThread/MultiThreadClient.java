package com.jinx.testnetty._015MultiThread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class MultiThreadClient {
    public static void main(String[] args) throws IOException {
        SocketChannel client = SocketChannel.open();
//        client.bind(new InetSocketAddress(2235));
        client.connect(new InetSocketAddress("127.0.0.1", 2234));

//        client.write(StandardCharsets.UTF_8.encode("Hello Chengdu, it's 2023-02-22 16:19:10"));
        System.in.read();
    }
}
