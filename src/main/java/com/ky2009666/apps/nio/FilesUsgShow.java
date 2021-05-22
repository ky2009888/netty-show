package com.ky2009666.apps.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ky2009666
 * @description Files用法展示
 * @date 2021/5/22
 **/
@Slf4j(topic = "FilesUsgShow")
public class FilesUsgShow {
    /**
     * 通过Files的copy方法进行文件的copy操作.
     *
     * @param args 命令行参数.
     * @throws IOException IO异常.
     */
    public static void main(String[] args) throws IOException {
        //进行文件copy操作.
        //copyFiles();
        //目录的数目
        AtomicInteger dirsCount = new AtomicInteger();
        //文件的数目
        AtomicInteger filesCount = new AtomicInteger();
        Files.walkFileTree(Paths.get("C:\\java\\jdk-11.0.11\\bin"), new SimpleFileVisitor<Path>() {
            /**
             * Invoked for a directory before entries in the directory are visited.
             *
             * <p> Unless overridden, this method returns {@link FileVisitResult#CONTINUE
             * CONTINUE}.
             *
             * @param dir
             * @param attrs
             */
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                dirsCount.incrementAndGet();
                return super.preVisitDirectory(dir, attrs);
            }

            /**
             * Invoked for a file in a directory.
             *
             * <p> Unless overridden, this method returns {@link FileVisitResult#CONTINUE
             * CONTINUE}.
             *
             * @param file
             * @param attrs
             */
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                filesCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }
        });
        log.info("dirs:{}",dirsCount.intValue());
        log.info("files:{}",filesCount.intValue());
    }

    private static void copyFiles() throws IOException {
        Path from = Paths.get("F:\\workspace-netty\\netty-show\\src\\main\\java\\com\\ky2009666\\apps\\nio\\FileChannelUsgShow.java");
        Path to = Paths.get("F:\\workspace-netty\\netty-show\\src\\main\\resources\\FileChannelUsgShow.java");
        Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
    }
}
