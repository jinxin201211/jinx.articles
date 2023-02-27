package com.jinx.testnio._014BigData;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);

        Selector selector = Selector.open();
        server.register(selector, SelectionKey.OP_ACCEPT, null);

        server.bind(new InetSocketAddress(2233));

        while (true) {
            selector.select();

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();

                if (selectionKey.isAcceptable()) {
                    SocketChannel socketChannel = server.accept();
                    socketChannel.configureBlocking(false);
                    SelectionKey channelKey = socketChannel.register(selector, 0, null);

                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < 30000000; i++) {
                        stringBuilder.append("a");
                    }
                    ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(stringBuilder.toString());

                    int write = socketChannel.write(byteBuffer);
                    System.out.println(write);
                    if (byteBuffer.hasRemaining()) {
                        channelKey.interestOps(channelKey.interestOps() + SelectionKey.OP_WRITE);
                        channelKey.attach(byteBuffer);
                    }
                } else if (selectionKey.isWritable()) {
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    int write = socketChannel.write(byteBuffer);
                    System.out.println(write);
//                    if (byteBuffer.hasRemaining()) {
//                        selectionKey.interestOps(selectionKey.interestOps() + SelectionKey.OP_WRITE);
//                        selectionKey.attach(byteBuffer);
//                    }
                    if (!byteBuffer.hasRemaining()) {
                        selectionKey.attach(null);
                        selectionKey.interestOps(selectionKey.interestOps() - SelectionKey.OP_WRITE);
                    }
                }
            }
        }
    }
}
