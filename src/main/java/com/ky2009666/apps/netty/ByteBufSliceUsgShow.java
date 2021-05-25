package com.ky2009666.apps.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ky2009666
 * @description ByteBuf切片操作
 * @date 2021/5/24
 **/
@Slf4j(topic = "byteBuf的切片方法是内存零拷贝")
public class ByteBufSliceUsgShow {
    public static void main(String[] args) {
        log.info("开始");
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(4);
        buffer.writeBytes(new byte[]{'1', '2', '3', '4'});
        ByteBuf byteBuf1 = buffer.slice(0, 2);
        ByteBuf byteBuf2 = buffer.slice(2, 2);
        System.out.println(ByteBufUtil.prettyHexDump(buffer));
        System.out.println(ByteBufUtil.prettyHexDump(byteBuf1));
        System.out.println(ByteBufUtil.prettyHexDump(byteBuf2));
        log.info("结束");
    }
}
