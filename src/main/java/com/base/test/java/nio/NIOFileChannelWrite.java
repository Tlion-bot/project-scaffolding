package com.base.test.java.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author nnc
 * @date 2024/1/19 10:58
 * NIO filechannel写数据到d盘  把str写入buffer->buffer反转->filechannel写入buffer
 */
public class NIOFileChannelWrite {

    public static void main(String[] args) throws IOException {
        String str="filechannel";
        FileOutputStream fileOutputStream = new FileOutputStream("d:\\fileout.txt");
        FileChannel channel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes(StandardCharsets.UTF_8));
        byteBuffer.flip();
        channel.write(byteBuffer);
        fileOutputStream.close();
    }

}
