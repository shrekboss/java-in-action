package org.crayzer.design.design_mode_pattern.creational.singleton.lazy;

/**
 * class_name: Lazy_v4_VolatileDoubleCheckSingleton
 * package: org.crayzer.demo.singleton.lazy
 * describe:
 * <p>
 *     优点：
 *          1.避免了饿汉式的那种在没有用到的情况下创建实例，资源利用率高
 *          2.线程安全
 *
 *      缺点：
 *          1.执行该类的其他静态方法或者加载了该类（class.forName)，那么这个实例仍然初始化
 *          2.禁止指令重排 导致性能下降
 * </p>
 * author: yizhe.chen
 * create_date: 2018/5/6
 * create_time: 下午10:45
 **/
public class Lazy_v4_VolatileDoubleCheckSingleton {
    /** volatile关键字的一个作用是禁止指令重排，把instance声明为volatile之后，对它的写操作就会有一个内存屏障 */
    private static volatile Lazy_v4_VolatileDoubleCheckSingleton instance;

    private Lazy_v4_VolatileDoubleCheckSingleton() {
    }

    public static Lazy_v4_VolatileDoubleCheckSingleton getInstance() {
        if (instance == null) {
            synchronized (Lazy_v4_VolatileDoubleCheckSingleton.class) {
                if (instance == null) {
                    instance = new Lazy_v4_VolatileDoubleCheckSingleton();
                }
            }
        }
        return instance;
    }
}
