package org.crayzer.design.design_mode_pattern.creational.singleton.lazy;

/**
 * class_name: Lazy_v1_SimpleSingleton
 * package: org.crayzer.demo.singleton.lazy
 * describe:
 * <p>
 *     优点：
 *          1.避免了饿汉式的那种在没有用到的情况下创建实例，资源利用率高
 *
 *      缺点：
 *          1.线程不安全
 *          2.执行该类的其他静态方法或者加载了该类（class.forName)，那么这个实例仍然初始化
 * </p>
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 **/
public class Lazy_v1_SimpleSingleton {
    private static Lazy_v1_SimpleSingleton instance;

    private Lazy_v1_SimpleSingleton() {
    }

    public static Lazy_v1_SimpleSingleton getInstance() {
        if (instance == null) {
            instance = new Lazy_v1_SimpleSingleton();
        }
        return instance;
    }

}

