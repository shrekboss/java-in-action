package org.crayzer.design.design_mode_pattern.creational.singleton.lazy;

import org.crayzer.design.design_mode_pattern.creational.singleton.hungry.HungrySingleton;

/**
 * class_name: Lazy_v3_DoubleCheckSingleton
 * package: org.crayzer.demo.singleton.lazy
 * describe:
 * <p>
 *     优点：
 *          1.避免了饿汉式的那种在没有用到的情况下创建实例，资源利用率高
 *          2.线程安全
 *
 *      缺点：
 *          1.执行该类的其他静态方法或者加载了该类（class.forName)，那么这个实例仍然初始化
 *          2.由于java内存模型一些原因偶尔失败
 *              非原子操作
 *                  a. 给 instance 分配内存
 *                  b. 调用 instance 的构造函数来初始化成员变量，形成实例
 *                  c. 将 instance 对象指向分配的内存空间（执行完这步 singleton才是非 null 了）
 *                     --> 123 or 132
 *                  结论：如果是后者，则在 3 执行完毕、2 未执行之前，被线程二抢占了，这时 instance 已经是非 null 了（但却没有初始化），
*                        所以线程二会直接返回 instance，然后使用，然后顺理成章地报错；
 *
 *                  由于有一个『instance已经不为null但是仍没有完成初始化』的中间状态，而这个时候，如果有其他线程刚好运行到第一层
 *                  if (instance == null)这里，这里读取到的instance已经不为null了，所以就直接把这个中间状态的instance拿去用了，
 *                  就会产生问题。
 *
 *              解决方法：
 *                  a. Lazy_v4_VolatileDoubleCheckSingleton 不让指令重排序
 *                  b. org.crayzer.demo.singleton.lazy.Lazy_v5_InnerClassSingleton 可以指令重排序，但是不让外部看见
 *
 * </p>
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 **/
public class Lazy_v3_DoubleCheckSingleton {
    private static Lazy_v3_DoubleCheckSingleton instance;

    private Lazy_v3_DoubleCheckSingleton() {
    }

    public static Lazy_v3_DoubleCheckSingleton getInstance() {
        if (instance == null) {
            synchronized (Lazy_v3_DoubleCheckSingleton.class) {
                if (instance == null) {
                    instance = new Lazy_v3_DoubleCheckSingleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        long count = 1000000;
        synchronizedSingleton(count);
        noSynchronizedSingleton(count);
    }

    private static void synchronizedSingleton(long count) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            Lazy_v3_DoubleCheckSingleton synchronizedSingleton = Lazy_v3_DoubleCheckSingleton.getInstance();
        }
        long end = System.currentTimeMillis();
        System.out.println("synchronizedSingleton 耗时：" + (end - start));
    }

    private static void noSynchronizedSingleton(long count) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            HungrySingleton hungrySingleton = HungrySingleton.getInstance();
        }
        long end = System.currentTimeMillis();
        System.out.println("noSynchronizedSingleton 耗时：" + (end - start));
    }
}
