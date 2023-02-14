package com.jinx.nettydemo.niomulti;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class TestClient {
    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress("127.0.0.1", 8090));
        channel.write(StandardCharsets.UTF_8.encode("1234567890abcdefghijk"));
        System.in.read();
    }
}
