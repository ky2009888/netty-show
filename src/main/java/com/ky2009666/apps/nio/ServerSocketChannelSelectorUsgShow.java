package com.ky2009666.apps.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author ky2009666
 * @description ServerSocketChannel用法
 * @date 2021/5/22
 **/
@Slf4j(topic = "ServerSocketChannelUsgShow")
public class ServerSocketChannelSelectorUsgShow {
    public static void main(String[] args) {
        ByteBuffer contentBuffer = ByteBuffer.allocate(1024);
        List<SocketChannel> socketChannelList = new ArrayList<>();
        try (ServerSocketChannel server = ServerSocketChannel.open();
             Selector selector = Selector.open();
        ) {
            //代表非阻塞模式
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(9999));
            //说明SelectionKey只关注accept事件
            SelectionKey selectionKey = server.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                //没有事件发生线程阻塞
                int select = selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey1 = iterator.next();
                    ServerSocketChannel channel = (ServerSocketChannel) selectionKey1.channel();
                    channel.accept();
                    log.debug("{}", channel);
                }
            }
        } catch (IOException e) {
            log.error("{}", e);
        }
    }
}
