package com.ky2009666.apps.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @author ky2009666
 * @description FileChannel的用法展示
 * @date 2021/5/22
 **/
@Slf4j(topic = "FileChannel")
public class FileChannelUsgShow {
    public static void main(String[] args) {
        try (FileChannel fileInputChannel = new FileInputStream("F:\\workspace-netty\\netty-show\\src\\main\\java\\com\\ky2009666\\apps\\nio\\ReadDataWithNioDemoV0.java").getChannel();
             FileChannel fileOutputChannel = new FileOutputStream("F:\\workspace-netty\\netty-show\\src\\main\\resources\\ReadDataWithNioDemoV0.java").getChannel();
        ) {
            //默认传输2G大小的文件
            //fileInputChannel.transferTo(0, fileInputChannel.size(), fileOutputChannel);
            //优化版本
            long size = fileInputChannel.size();
            //left 文件剩余大小
            for (long left = size; left > 0; ) {
                left -= fileInputChannel.transferTo(left - size, left, fileOutputChannel);
            }
        } catch (Exception e) {
            log.error("{}", e);
        }

    }
}
