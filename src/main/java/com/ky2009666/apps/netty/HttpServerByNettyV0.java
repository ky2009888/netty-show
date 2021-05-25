package com.ky2009666.apps.netty;

import com.sun.net.httpserver.HttpsParameters;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @author ky2009666
 * @description 通过netty构建http 的server服务器
 * @date 2021/5/25
 **/
@Slf4j(topic = "http服务器的netty版本")
public class HttpServerByNettyV0 {
    /**
     * 定义HttpServerByNettyV0的句柄.
     */
    private static HttpServerByNettyV0 httpServer;

    /**
     * 定义私有构造方法.
     */
    private HttpServerByNettyV0() {
    }

    static {
        httpServer = new HttpServerByNettyV0();
    }

    /**
     * 返回实例.
     *
     * @return HttpServerByNettyV0
     */
    public static HttpServerByNettyV0 getHttpServer() {
        return httpServer;
    }

    /**
     * 初始化web服务器.
     * BGCCB
     */
    public void initHttpServer() throws InterruptedException {
        //定义处理任务请求的线程组
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup(2);
        //定义启动类
        ServerBootstrap serverboot = new ServerBootstrap();
        //添加线程组
        serverboot.group(boss, worker);
        //添加channel
        serverboot.channel(NioServerSocketChannel.class);
        //初始化channel
        serverboot.childHandler(new ChannelInitializer<SocketChannel>() {
            /**
             * This method will be called once the {@link Channel} was registered. After the method returns this instance
             * will be removed from the {@link ChannelPipeline} of the {@link Channel}.
             *
             * @param ch the {@link Channel} which was registered.
             * @throws Exception is thrown if an error occurs. In that case it will be handled by
             *                   {@link #exceptionCaught(ChannelHandlerContext, Throwable)} which will by default close
             *                   the {@link Channel}.
             */
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                //指定http请求的编解码器
                ch.pipeline().addLast(new HttpServerCodec());
                //指定处理器
                ch.pipeline().addLast(new SimpleChannelInboundHandler<HttpRequest>() {
                    /**
                     * Is called for each message of type {@link I}.
                     *
                     * @param ctx the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
                     *            belongs to
                     * @param msg the message to handle
                     * @throws Exception is thrown if an error occurred
                     */
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, HttpRequest request) throws Exception {
                        String uri = request.uri();
                        log.info("uri:{}", uri);
                        HttpMethod method = request.method();
                        log.info("HttpMethod:{}", method.toString());
                        //指定请求的响应协议版本和请求状态
                        DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.OK);
                        String content = "<h1>hello world to my life!!!!!</h1>";
                        byte[] contentBytes = content.getBytes();
                        httpResponse.headers().setInt("content-length", contentBytes.length);
                        httpResponse.content().writeBytes(contentBytes);
                        ctx.writeAndFlush(httpResponse);
                    }
                });
            }
        });
        ChannelFuture channelFuture = serverboot.bind(8989).sync();
        //同步等待
        channelFuture.channel().closeFuture().sync();
    }

    /**
     * 命令行初始化.
     *
     * @param args 命令行参数.
     */
    public static void main(String[] args) throws InterruptedException {
        //初始化服务器
        HttpServerByNettyV0.getHttpServer().initHttpServer();
    }
}
