package com.jinx.testnetty._013Selector;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.jinx.nettydemo.base.ByteBufferUtil.debugAll;
import static com.jinx.testnetty.ByteBufferUtil.debugRead;

@Slf4j
public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);

        Selector selector = Selector.open();
        SelectionKey serverKey = server.register(selector, 0, null);
        serverKey.interestOps(SelectionKey.OP_ACCEPT);

        server.bind(new InetSocketAddress(2233));

        ByteBuffer buffer = ByteBuffer.allocate(16);
        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            if (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                log.info("key: {}", selectionKey);

                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel selectServer = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel channel = selectServer.accept();
                    channel.configureBlocking(false);

                    SelectionKey channelKey = channel.register(selector, 0, null);
                    channelKey.interestOps(SelectionKey.OP_READ);
                    log.info("channel: {}", channel);
                } else if (selectionKey.isReadable()) {
                    try {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        int read = channel.read(buffer);
                        if (read == -1) {
                            selectionKey.cancel();
                        } else {
                            splitBuffer(buffer);
//                            buffer.flip();
//                            debugRead(buffer);
//                            buffer.clear();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        selectionKey.cancel();
                    }
                }
                iterator.remove();
            }
        }
    }

    public static void splitBuffer(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            if ((char) source.get(i) == '\n') {
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
