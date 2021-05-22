package com.ky2009666.apps.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author ky2009666
 * @description 通过NIO读取数据
 * @date 2021/5/21
 **/
@Slf4j(topic = "通过NIO读取数据")
public class ReadDataWithNioDemoV0 {
    /**
     * @param args
     */
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.put((byte) 'a');
        byteBuffer.flip();
        byte b = byteBuffer.get();
        log.info("{}", (char) b);
        byteBuffer.clear();
    }

    public static void show2(String[] args) {
        try (FileChannel channel
                     = new FileInputStream("F:\\workspace-netty\\netty-show\\src\\main\\resources\\data.txt")
                .getChannel()) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(10);
            while (true) {
                int len = channel.read(byteBuffer);
                log.info("读取到的字节数:{}", len);
                if (len == -1) {
                    break;
                }
                //切换至读模式
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    byte bChar = byteBuffer.get();
                    log.info("{}", (char) bChar);
                }
                //切换至写模式
                byteBuffer.clear();
            }
        } catch (IOException e) {
            log.error("error:", e);
        }
    }
}
