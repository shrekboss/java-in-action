package org.crayzer.conc.juc;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者模型
 *
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 * @since 1.0.0
 */
public class TestProductorAndConsumer {

    @Test
    public void productorAndConsumerForSynchronizedTest() {
        Clerk clerk = new Clerk();
        Productor productor = new Productor(clerk);
        Consumer consumer = new Consumer(clerk);

        //没有等待唤醒机制的时候
        //生产者一直生产 不考虑消费者  可能造成数据丢失
        //消费者一直消费 不考虑生产者  可能造成重复消费
        new Thread(productor, "生产者a").start();
        new Thread(productor, "生产者b").start();
        new Thread(consumer, "消费者a").start();
    }

    @Test
    public void productorAndConsumerForLockTest() {
        ClerkLock clerkLock = new ClerkLock();
        Productor productor = new Productor(clerkLock);
        Consumer consumer = new Consumer(clerkLock);

        //没有等待唤醒机制的时候
        //生产者一直生产 不考虑消费者  可能造成数据丢失
        //消费者一直消费 不考虑生产者  可能造成重复消费
        new Thread(productor, "生产者a").start();
        new Thread(productor, "生产者b").start();
        new Thread(consumer, "消费者a").start();
    }
}

/**
 * 店员
 */
class Clerk {
    //库存共享数据 存在安全问题
    private int product = 0;

    //进货
    public synchronized void get() {
        if (product >= 10) {
            System.out.println("产品已满，无法添加");
            try {
                this.wait();
            } catch (InterruptedException e) {
            }
        } else {
            this.notifyAll();
            System.out.println(Thread.currentThread().getName() + "店员进货1个产品 库存为" + ++product);
        }
    }

    //卖货
    public synchronized void sale() {
        if (product <= 0) {
            System.out.println("产品缺货，无法售卖");
            try {
                this.wait();
            } catch (InterruptedException e) {
            }
        } else {
            System.out.println(Thread.currentThread().getName() + "店员销售1个产品 库存为" + --product);
            this.notifyAll();
        }
    }

    /**
     * 进货
     * <p>虚假唤醒。解决方法，if换为while循环执行
     */
    public synchronized void get1() {
        while (product >= 1) {
            System.out.println("产品已满，无法添加");
            try {
                this.wait();
            } catch (InterruptedException e) {
            }
        }
        this.notifyAll();
        System.out.println(Thread.currentThread().getName() + "店员进货1个产品 库存为" + ++product);
    }

    /**
     * 卖货
     * <p>消费者等于0的时候， 两个消费者同时生产，之后停住了，没有其他线程去唤醒，导致停在生产者这里。解决方法，去掉else，让他能走唤醒方法。
     * <p>虚假唤醒。解决方法，if换为while循环执行
     */
    public synchronized void sale1() {
        while (product <= 0) {
            System.out.println("产品缺货，无法售卖");
            try {
                this.wait();
            } catch (InterruptedException e) {
            }
        }
        System.out.println(Thread.currentThread().getName() + "店员销售1个产品 库存为" + --product);
        this.notifyAll();
    }
}

/**
 * 生产者
 */
class Productor implements Runnable {
    private Clerk clerk;
    private ClerkLock clerkLock;

    public Productor(Clerk clerk) {
        this.clerk = clerk;
    }
    public Productor(ClerkLock clerkLock) {
        this.clerkLock = clerkLock;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
//            clerk.get();
//            clerk.get1();

            clerkLock.get();
        }
    }
}

/**
 * 消费者
 */
class Consumer implements Runnable {
    private Clerk clerk;
    private ClerkLock clerkLock;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }
    public Consumer(ClerkLock clerkLock) {
        this.clerkLock = clerkLock;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
//            clerk.sale();
//            clerk.sale1();

            clerkLock.sale();
        }
    }
}

/**
 * 店员
 */
class ClerkLock {
    //库存共享数据 存在安全问题
    private int product = 0;
    //使用lock，去掉synchronized   this.wait 和 lock就是两把锁，用lock统一
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    //进货
    public void get() {
        lock.lock();
        try {
            while (product >= 1) {
                System.out.println("产品已满，无法添加");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                }
            }
            condition.signalAll();
            System.out.println(Thread.currentThread().getName() + "店员进货1个产品 库存为" + ++product);
        } finally {
            lock.unlock();
        }
    }

    //卖货
    public synchronized void sale() {
        lock.lock();
        try {
            while (product <= 0) {
                System.out.println("产品缺货，无法售卖");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                }
            }
            System.out.println(Thread.currentThread().getName() + "店员销售1个产品 库存为" + --product);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

