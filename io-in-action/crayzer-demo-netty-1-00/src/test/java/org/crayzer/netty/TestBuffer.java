package org.crayzer.netty;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
public class TestBuffer {


    @Test
    public void test3() {
        // 分配直接缓冲区
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        System.out.println(buffer.isDirect());

        buffer = ByteBuffer.allocate(1024);
        System.out.println(buffer.isDirect());
    }

    @Test
    public void test2() {
        String str = "abcde";
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.put(str.getBytes());
        buf.flip();
        byte[] dst = new byte[buf.limit()];
        buf.get(dst, 0, 2);
        System.out.println(new String(dst, 0, 2));
        System.out.println("position: " + buf.position());
        System.out.println("capacity: " + buf.capacity());
        System.out.println("limit: " + buf.limit());

        buf.mark();
        System.out.println("----------------mark----------------");
        buf.get(dst, 2, 2);
        System.out.println(new String(dst, 2, 2));
        System.out.println("position: " + buf.position());
        System.out.println("capacity: " + buf.capacity());
        System.out.println("limit: " + buf.limit());

        buf.reset();
        System.out.println("----------------reset----------------");
        System.out.println("position: " + buf.position());
        System.out.println("capacity: " + buf.capacity());
        System.out.println("limit: " + buf.limit());

        // 判断缓冲区是否还存在数据
        if (buf.hasRemaining()) {
            System.out.println(buf.remaining());
        }
    }

    /**
     * 0 <= mark <= position <= limit <= capacity
     */
    @Test
    public void test1() {
        String str = "abcde";

        // 现分配一个指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        System.out.println("----------------allocate----------------");
        System.out.println("position: " + buf.position());
        System.out.println("capacity: " + buf.capacity());
        System.out.println("limit: " + buf.limit());

        // 利用put()存入数据到缓冲区
        buf.put(str.getBytes());
        System.out.println("----------------put----------------");
        System.out.println("position: " + buf.position());
        System.out.println("capacity: " + buf.capacity());
        System.out.println("limit: " + buf.limit());

        // 切换读取数据模式
        buf.flip();
        System.out.println("----------------flip----------------");
        System.out.println("position: " + buf.position());
        System.out.println("capacity: " + buf.capacity());
        System.out.println("limit: " + buf.limit());

        // 通过get读取数据模式
        byte[] dst = new byte[buf.limit()];
        System.out.println("----------------get----------------");
        buf.get(dst);
        System.out.println(new String(dst, 0, dst.length));
        System.out.println("position: " + buf.position());
        System.out.println("capacity: " + buf.capacity());
        System.out.println("limit: " + buf.limit());

        // 可以重复读数据
        buf.rewind();
        System.out.println("----------------rewind----------------");
        System.out.println("position: " + buf.position());
        System.out.println("capacity: " + buf.capacity());
        System.out.println("limit: " + buf.limit());

        // 清空缓冲区,但是缓中区的数据依然存在,处于"被遗忘"状态
        buf.clear();
        System.out.println("----------------clear----------------");
        System.out.println("position: " + buf.position());
        System.out.println("capacity: " + buf.capacity());
        System.out.println("limit: " + buf.limit());
        System.out.println((char)buf.get());
    }
}
