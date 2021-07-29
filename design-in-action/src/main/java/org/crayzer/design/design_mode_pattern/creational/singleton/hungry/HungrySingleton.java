package org.crayzer.design.design_mode_pattern.creational.singleton.hungry;

import java.util.concurrent.CountDownLatch;

/**
 * class_name: HungrySingleton
 * package: org.crayzer.demo.singleton.hungry
 * describe:
 * <p>
 *     优点：
 *          1.线程安全；
 *          2.在类加载的同时已经创建好一个静态对象，调用时反应速度快
 *
 *     缺点：
 *          1.资源效率不高，可能getInstance()永远不会执行到
 *          2.执行该类的其他静态方法或者加载了该类（class.forName)，那么这个实例仍然初始化
 * </p>
 * author: yizhe.chen
 * create_date: 2018/5/6
 * create_time: 下午10:45
 **/
public class HungrySingleton {

    private final static HungrySingleton instance = new HungrySingleton();

    private HungrySingleton() {
    }

    public static HungrySingleton getInstance() {
        return instance;
    }

    public static void main(String[] args) {

        /** 线程安全 */
        int count = 1000;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                HungrySingleton instance = HungrySingleton.getInstance();
                System.out.println(System.currentTimeMillis() + ":" + instance);
                countDownLatch.countDown();
            }).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /** 通过反射 那么这个实例仍然初始化 */
        // try {
        //     Class<?> clazz = Class.forName("org.crayzer.demo.singleton.hungry.HungrySingleton");
        //     Object instance1 = clazz.newInstance();
        //     Object instance2 = clazz.newInstance();
        //
        //     System.out.println(instance1);
        //     System.out.println(instance2);
        // } catch (ClassNotFoundException e) {
        //     e.printStackTrace();
        // } catch (IllegalAccessException e) {
        //     e.printStackTrace();
        // } catch (InstantiationException e) {
        //     e.printStackTrace();
        // }
    }
}
