package com.ky2009666.apps.netty;

import com.sun.xml.internal.stream.util.BufferAllocator;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.CompositeByteBuf;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ky2009666
 * @description ByteBuffer使用说明
 * @date 2021/5/24
 **/
@Slf4j(topic = "ByteBuffer的用法")
public class ByteBufferUsgShow {
    public static void main(String[] args) {
        ByteBuf buffer1 = ByteBufAllocator.DEFAULT.buffer(10);
        buffer1.writeBytes(new byte[]{'a', 'b', 'c', 'd'});
        ByteBuf buffer2 = ByteBufAllocator.DEFAULT.buffer(10);
        buffer2.writeBytes(new byte[]{'a', 'b', 'c', 'd'});
        CompositeByteBuf buffer3 = ByteBufAllocator.DEFAULT.compositeBuffer(20);
        buffer3.addComponents(true,buffer1,buffer2);
        System.out.println(ByteBufUtil.prettyHexDump(buffer3));
    }

    public static void main2(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10);
        buffer.writeBytes(new byte[]{'a', 'b', 'c', 'd'});
        ByteBuf buffer2 = ByteBufAllocator.DEFAULT.buffer(10);
        buffer2.writeBytes(new byte[]{'a', 'b', 'c', 'd'});
        buffer2.readByte();
        //log.info("{}", ByteBufUtil.prettyHexDump(buffer));
        System.out.println(ByteBufUtil.prettyHexDump(buffer));
    }
}
