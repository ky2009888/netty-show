package com.ky2009666.apps.netty;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author ky2009666
 * @description EventLoop用法展示
 * @date 2021/5/23
 **/
@Slf4j(topic = "EventLoopGroupUsgShow")
public class EventLoopGroupUsgShow {
    /**
     * 入口方法
     *
     * @param args
     */
    public static void main(String[] args) {
        int processors = NettyRuntime.availableProcessors();
        log.info("{}", processors);
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup(2);
        eventExecutors.execute(() -> {
            log.info("执行普通的线程池任务");
        });
        eventExecutors.next().scheduleWithFixedDelay(() -> {
            log.info("定时执行");
        }, 2, 3, TimeUnit.SECONDS);
    }
}
