package com.jinx.nettydemo.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

@Slf4j
public class WriteServer {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();

        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false); //非阻塞模式

        SelectionKey serverKey = server.register(selector, 0, null);
        serverKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("register key: {}", serverKey);

        server.bind(new InetSocketAddress(8888));
        while (true) {
            // 没有事件发生，线程阻塞，有事件，线程恢复运行
            // 有事件没有处理时，不会阻塞，事件发生之后，要么处理要么取消
            selector.select();

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                log.debug("key: {}", key);

                if (key.isAcceptable()) {
                    ServerSocketChannel socket = (ServerSocketChannel) key.channel();
                    SocketChannel channel = socket.accept();
                    channel.configureBlocking(false);
                    SelectionKey channelKey = channel.register(selector, 0, null);

                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < 10000000; i++) {
                        sb.append("a");
                    }
                    ByteBuffer buffer = StandardCharsets.UTF_8.encode(sb.toString());
                    int size = channel.write(buffer);
                    log.debug("实际写入：{}", size);
                    if (buffer.hasRemaining()) {
                        channelKey.interestOps(channelKey.interestOps() + SelectionKey.OP_WRITE);
                        channelKey.attach(buffer);
                    }
                } else if (key.isWritable()) {
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    SocketChannel channel = (SocketChannel) key.channel();
                    int size = channel.write(buffer);
                    log.debug("实际写入：{}", size);
                    if (!buffer.hasRemaining()) {
                        key.attach(null);
                        key.interestOps(key.interestOps() - SelectionKey.OP_WRITE);
                    }
                }
            }
        }
    }
}
