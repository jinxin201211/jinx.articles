package com.jinx.nettydemo.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import static com.jinx.nettydemo.base.ByteBufferUtil.debugAll;

@Slf4j
public class ServerSelector {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();

        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false); //非阻塞模式

        SelectionKey serverKey = server.register(selector, 0, null);
        serverKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("register key: {}", serverKey);

        server.bind(new InetSocketAddress(8899));
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
                    ByteBuffer buffer = ByteBuffer.allocate(32);
                    SelectionKey channelKey = channel.register(selector, 0, buffer);
                    channelKey.interestOps(SelectionKey.OP_READ);
                    log.debug("socketchannel: {}", channel);
                } else if (key.isReadable()) {
                    try {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        int read = channel.read(buffer);
                        if (read > 0) {
                            split(buffer);
                            if (buffer.position() == buffer.limit()) {
                                ByteBuffer buffer1 = ByteBuffer.allocate(buffer.capacity() * 2);
                                buffer.flip();
                                buffer1.put(buffer);
                                key.attach(buffer1);
                            }
//                            buffer.flip();
//                          System.out.println(StandardCharsets.UTF_8.decode(buffer));
//                            debugAll(buffer);
//                          buffer.clear();
                            log.debug("after read {}", channel);
                        } else {
                            key.cancel();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        key.cancel(); //客户端突然中断的时候会发出一个写操作，所以需要在这将这个写操作关闭，不然这个写操作会造成无限循环
                    }
                }
            }
        }
    }


    public static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            if (source.get(i) == '\n') {
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                debugAll(target);
            }
        }
        source.compact();
    }
}
