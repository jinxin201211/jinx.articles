package com.jinx.testnetty._014BigData;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel client = SocketChannel.open();
        client.connect(new InetSocketAddress(2233));
        int count = 0;
        while (true) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            count += client.read(byteBuffer);
            System.out.println(count + ":" + byteBuffer);
            byteBuffer.clear();
        }
    }
}
