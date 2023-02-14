package com.jinx.nettydemo.niomulti;

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
public class MultiThreadServer {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("boss");
        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);

        Selector boss = Selector.open();
        SelectionKey bossKey = server.register(boss, SelectionKey.OP_ACCEPT, null);
        server.bind(new InetSocketAddress(8090));
        Worker worker = new Worker("worker-0");
        worker.register();
        while (true) {
            boss.select();
            Iterator<SelectionKey> iterator = boss.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                    SocketChannel channel = serverChannel.accept();
                    log.debug("========> connected {}", channel);
                    channel.configureBlocking(false);

                    log.debug("========> before register {}", channel.getRemoteAddress());
                    channel.register(worker.selector, SelectionKey.OP_READ, null);
                    log.debug("========> after register {}", channel.getRemoteAddress());
                }
            }
        }
    }

    static class Worker implements Runnable {
        private Thread thread;
        private Selector selector;
        private String name;
        private volatile boolean start = false;

        public Worker(String name) {
            this.name = name;
        }

        public void register() throws IOException {
            if (!this.start) {
                this.thread = new Thread(this, name);
                this.selector = Selector.open();
                this.start = true;
                this.thread.start();
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    this.selector.select();
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            SocketChannel channel = (SocketChannel) key.channel();
                            log.debug("========> read {}", channel.getRemoteAddress());
                            channel.read(buffer);
                            buffer.flip();
                            debugAll(buffer);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
