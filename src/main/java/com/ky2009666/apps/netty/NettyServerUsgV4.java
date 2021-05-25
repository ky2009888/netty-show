package com.ky2009666.apps.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ky2009666
 * @description netty server服务
 * @date 2021/5/24
 **/
@Slf4j(topic = "NettyServerUsgV4 服务")
public class NettyServerUsgV4 {
    public static void main(String[] args) {
        new ServerBootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new StringDecoder());
                        nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                String byteBuf = (String) msg;
                                log.info("服务器接收客户端的内容:{}", msg.toString());
                                ByteBuf response = ctx.alloc().buffer();
                                response.writeBytes(byteBuf.getBytes());
                                ctx.writeAndFlush(response);
                            }
                        });
                    }
                }).bind(8989);
    }
}
