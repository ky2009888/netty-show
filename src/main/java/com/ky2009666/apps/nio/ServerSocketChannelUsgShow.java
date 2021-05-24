package com.ky2009666.apps.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author ky2009666
 * @description ServerSocketChannel用法
 * @date 2021/5/22
 **/
@Slf4j(topic = "ServerSocketChannelUsgShow")
public class ServerSocketChannelUsgShow {
    public static void main(String[] args) {
        ByteBuffer contentBuffer = ByteBuffer.allocate(1024);
        List<SocketChannel> socketChannelList = new ArrayList<>();
        try (ServerSocketChannel server = ServerSocketChannel.open();
        ) {
            //代表非阻塞模式
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(9999));
            //说明SelectionKey只关注accept事件
            while (true) {
               SocketChannel accept = server.accept();
                if (accept != null) {
                    socketChannelList.add(accept);
                    for (SocketChannel socket : socketChannelList
                    ) {
                        if (socket != null) {
                            socket.read(contentBuffer);
                            contentBuffer.flip();
                            CharBuffer decode = StandardCharsets.UTF_8.decode(contentBuffer);
                            log.info("接收到的内容:{}", decode.toString());
                            contentBuffer.clear();
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error("{}", e);
        }
    }
}
