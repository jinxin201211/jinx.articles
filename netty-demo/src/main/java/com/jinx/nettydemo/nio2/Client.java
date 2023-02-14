package com.jinx.nettydemo.nio2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress("127.0.0.1", 8088));

        channel.write(StandardCharsets.UTF_8.encode("1234567890qwertyuiopasdfghjklzxcvbnm\n!@#$%^&*()_+\n"));

        channel.close();
    }
}
