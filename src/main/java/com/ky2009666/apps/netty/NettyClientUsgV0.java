package com.ky2009666.apps.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ky2009666
 * @description netty客户端
 * @date 2021/5/23
 **/
@Slf4j(topic = "netty客户端")
public class NettyClientUsgV0 {
    /**
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        //1、启动类
        new Bootstrap()
                //2、添加EventLoop
                .group(new NioEventLoopGroup())
                //3、选择Channel
                .channel(NioSocketChannel.class)
                //4、添加处理器
                .handler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new StringEncoder());
                    }
                })
                //5、连接到服务器
                .connect("localhost", 8989)
                //阻塞方法，等待建立连接
                .sync()
                //代表连接对象
                .channel()
                //6、向服务器发送数据
                .writeAndFlush("hello netty server");
    }
}
