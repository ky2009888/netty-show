package com.ky2009666.apps.netty;

import io.netty.bootstrap.ServerBootstrap;
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
 * @description netty服务端代码
 * @date 2021/5/23
 **/
@Slf4j(topic = "netty服务端")
public class NettyServerUsgV0 {
    public static void main(String[] args) {
        //1、服务器端启动类
        new ServerBootstrap()
                //2、BossEventLoopGroup WorkerEventLoop group 组
                .group(new NioEventLoopGroup())
                //3、选择服务器的ServerSocketChannel
                .channel(NioServerSocketChannel.class)
                //4、添加处理器
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    //5、数据读写的通道进行初始化
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        //添加具体处理的handler
                        nioSocketChannel.pipeline().addLast(new StringDecoder());
                        nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                super.channelRead(ctx, msg);
                                log.info("{}", msg);
                            }
                        });
                    }
                })
                //绑定端口号
                .bind(8989);
    }
}
