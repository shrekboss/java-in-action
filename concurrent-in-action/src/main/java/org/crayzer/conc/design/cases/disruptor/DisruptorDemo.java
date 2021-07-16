package org.crayzer.conc.design.cases.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;

public class DisruptorDemo {
    public static void main(String[] args) throws InterruptedException {

        //指定RingBuffer大小,
        //必须是2的N次方
        int bufferSize = 1024;
        //构建Disruptor
        Disruptor<LongEvent> disruptor
                = new Disruptor<>(
                LongEvent::new,
                bufferSize,
                DaemonThreadFactory.INSTANCE);
        //注册事件处理器
        disruptor.handleEventsWith(
                (event, sequence, endOfBatch) ->
                        System.out.println("E: " + event));
        //启动Disruptor
        disruptor.start();
        //获取RingBuffer
        RingBuffer<LongEvent> ringBuffer
                = disruptor.getRingBuffer();
        //生产Event
        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            bb.putLong(0, l);
            //生产者生产消息
            ringBuffer.publishEvent(
                    (event, sequence, buffer) ->
                            event.set(buffer.getLong(0)), bb);
            Thread.sleep(1000);
        }
    }
}
