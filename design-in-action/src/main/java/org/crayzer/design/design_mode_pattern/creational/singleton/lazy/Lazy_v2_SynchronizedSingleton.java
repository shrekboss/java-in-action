package org.crayzer.design.design_mode_pattern.creational.singleton.lazy;

/**
 * class_name: Lazy_v2_SynchronizedSingleton
 * package: org.crayzer.demo.singleton.lazy
 * describe:
 * <p>
 *     优点：
 *          1.避免了饿汉式的那种在没有用到的情况下创建实例，资源利用率高
 *          2.线程安全
 *
 *      缺点：
 *          1.第一次加载时不够快，多线程使用不必要的同步开销大
 *          2.执行该类的其他静态方法或者加载了该类（class.forName)，那么这个实例仍然初始化
 * </p>
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 **/
public class Lazy_v2_SynchronizedSingleton {
    private static Lazy_v2_SynchronizedSingleton instance;

    private Lazy_v2_SynchronizedSingleton() {
    }

    public synchronized static Lazy_v2_SynchronizedSingleton getInstance() {
        if (instance == null) {
            instance = new Lazy_v2_SynchronizedSingleton();
        }
        return instance;
    }

    public static void main(String[] args) {

        try {
            Class<?> clazz = Class.forName("org.crayzer.demo.singleton.lazy.Lazy_v2_SynchronizedSingleton");
            Object instance1 = clazz.newInstance();
            Object instance2 = clazz.newInstance();
            System.out.println(instance1);
            System.out.println(instance2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
