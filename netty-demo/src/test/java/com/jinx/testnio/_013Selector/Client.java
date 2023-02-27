package com.jinx.testnio._013Selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel client = SocketChannel.open();
        client.connect(new InetSocketAddress("127.0.0.1", 2233));
        System.out.println("Waiting...");

        int count = 0;
        while (true) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);
            count += client.read(byteBuffer);
            byteBuffer.clear();
        }

        //client.write(StandardCharsets.UTF_8.encode("Hello!!! World!!!"))
    }
}
