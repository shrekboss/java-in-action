package org.crayzer.conc.juc.tool.semaphore;


import java.util.Queue;

/**
 * Semaphore
 */
public class SemaphoreDesc {
    // 计数器
    int count;
    // 等待队列
    Queue queue;
    // 初始化操作
    SemaphoreDesc(int c) {
        this.count = c;
    }

    /**
     * 对应的是 acquire()
     * 如果此时计数器的值小于 0，则当前线程将被阻塞，否则当前线程可以继续执行
     */
    void down() {
        this.count--;
        if (this.count < 0) {
            //将当前线程插入等待队列
            //阻塞当前线程
        }
    }

    /**
     * 对应的是 release()
     * 如果此时计数器的值小于或者等于 0，则唤醒等待队列中的一个线程，并将其从等待队列中移除。
     */
    void up() {
        this.count++;
        if (this.count <= 0) {
            //移除等待队列中的某个线程T
            //唤醒线程T
        }
    }
}
