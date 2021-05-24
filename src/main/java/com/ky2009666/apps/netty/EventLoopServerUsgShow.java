package com.ky2009666.apps.netty;/**
 * @title: EventLoopServerUsgShow
 * @projectName netty-show
 * @description: TODO
 * @author Lenovo
 * @date 2021/5/23 11:41
 */

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author ky2009666
 * @description EventLoop用法展示
 * @date 2021/5/23
 **/
@Slf4j
public class EventLoopServerUsgShow {
    /**
     * 命令行如何方法.
     * SGCCB
     *
     * @param args
     */
    public static void main(String[] args) {
        new ServerBootstrap()
                .group(new NioEventLoopGroup(1), new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                log.info("服务器接收客户端的内容:{}", byteBuf.toString(Charset.defaultCharset()));
                            }
                        });
                    }
                }).bind(8989);
    }
}
