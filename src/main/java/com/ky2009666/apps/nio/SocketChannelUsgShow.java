package com.ky2009666.apps.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @author ky2009666
 * @description SocketChannel使用
 * @date 2021/5/22
 **/
@Slf4j(topic = "SocketChannelUsgShow")
public class SocketChannelUsgShow {
    public static void main(String[] args) {
        try (SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.connect(new InetSocketAddress("localhost", 9999));
            socketChannel.configureBlocking(false);
            log.info("等待中--------------");
            socketChannel.write(Charset.defaultCharset().encode("您好，服务器"));
        } catch (IOException e) {
            log.error("{}", e);
        }
    }
}
