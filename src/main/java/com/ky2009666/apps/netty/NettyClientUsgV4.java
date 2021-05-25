package com.ky2009666.apps.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @author ky2009666
 * @description netty 客户端
 * @date 2021/5/24
 **/
@Slf4j(topic = "NettyClientUsgV4客户端04")
public class NettyClientUsgV4 {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Channel channel = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new StringEncoder());
                        //ChannelInboundHandlerAdapter
                        nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                log.info("{}", byteBuf.toString(Charset.defaultCharset()));
                            }
                        });
                    }
                }).connect("localhost", 8989).sync().channel();
        channel.closeFuture().addListener(future -> {
            group.shutdownGracefully();
        });
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                if (!StringUtils.isEmpty(line)) {
                    if ("exit".equals(line)) {
                        channel.close();
                        break;
                    }
                    channel.writeAndFlush(line);
                }
            }
        }, "回复内容多线程").start();
    }
}
