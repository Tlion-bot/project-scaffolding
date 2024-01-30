package com.base.test.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author nnc
 * @date 2024/1/19 10:58
 * NIO filechannel 从d盘中读取fileout.txt 把fileout.txt写入filechannel->把filechannel数据写入buffer->冲buffer中读取数据
 */
public class NIOFileChannelRead {

    public static void main(String[] args) throws IOException {
        File file = new File("d:\\fileout.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        channel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();
    }

}
