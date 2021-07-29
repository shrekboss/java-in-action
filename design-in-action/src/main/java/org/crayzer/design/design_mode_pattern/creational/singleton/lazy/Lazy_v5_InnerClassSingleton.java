package org.crayzer.design.design_mode_pattern.creational.singleton.lazy;

import java.lang.reflect.Constructor;

/**
 * class_name: Lazy_v5_InnerClassSingleton
 * package: org.crayzer.demo.singleton.lazy
 * describe:
 * <p>
 *     优点：
 *          1.避免了饿汉式的那种在没有用到的情况下创建实例，资源利用率高
 *          2.线程安全
 *
 *          比较完美的一种懒加载单例实现方法
 *
 * </p>
 * author: yizhe.chen
 * create_date: 2018/5/6
 * create_time: 下午10:45
 **/
public class Lazy_v5_InnerClassSingleton {

    /**
     * static 是为了使单例的空间共享
     * final 保证这个方法不会被重写，重载
     */
    public static final Lazy_v5_InnerClassSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 这种写法非常巧妙：
     * 对于内部类 SingletonHolder，它是一个饿汉式的单例实现，在SingletonHolder初始化的时候会
     * 由ClassLoader来保证同步，使 instance 是一个真·单例。
     * 同时，由于SingletonHolder是一个内部类，只在外部类的Singleton的getInstance()中被使用，
     * 所以它被加载的时机也就是在getInstance()方法第一次被调用的时候。
     * <p>
     * 从内部看是一个饿汉式的单例，但是从外部看来，又的确是懒汉式的实现。
     */
    private static class SingletonHolder {
        private static final Lazy_v5_InnerClassSingleton INSTANCE = new Lazy_v5_InnerClassSingleton();
    }

    /** 防止 反射 攻击！ */
    private static boolean initialized = false;

    private Lazy_v5_InnerClassSingleton() {
        synchronized (Lazy_v5_InnerClassSingleton.class){
            if(!initialized){
                initialized = true;
            }
            else{
                throw new RuntimeException("单例已被侵犯");
            }
        }
    }

    public static void main(String[] args) {
        try {

            Class<?> clazz = Lazy_v5_InnerClassSingleton.class;
            Constructor c = clazz.getDeclaredConstructor(null);
            c.setAccessible(true);
            Object o = c.newInstance();
            System.out.println(o);
            Object o1 = c.newInstance();
            System.out.println(o1);
            Object o2 = c.newInstance();
            System.out.println(o2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
