package com.jinx.nettydemo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress("127.0.0.1", 8899));
        System.out.println("waiting");
        channel.write(StandardCharsets.UTF_8.encode("ClientClientClientClientClientClient\nSplit\n"));
//        channel.write(StandardCharsets.UTF_8.encode(String.valueOf(new SecureRandom().nextLong())));
        channel.close();
    }
}
