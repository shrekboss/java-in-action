package org.crayzer.design.design_mode_pattern.creational.singleton.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * class_name: RegisterSingleton
 * package: org.crayzer.demo.singleton.register
 * describe:
 * <p>
 *     优点：
 *          1.登记式模式可以被继承 (饿单例模式和懒单例模式构造方法都是私有的，因而是不能被继承的)
 *
 *     登记式单例实际上维护的是一组单例类的实例，将这些实例存储到一个Map(登记簿)
 *     中，对于已经登记过的单例，则从工厂直接返回，对于没有登记的，则先登记，而后
 *     返回
 * </p>
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 **/
public class RegisterSingleton {
    private static Map<String, RegisterSingleton> registerSingletonMap = new ConcurrentHashMap<>();

    public static Map<String, RegisterSingleton> getRegisterSingletonMap() {
        return registerSingletonMap;
    }

    protected RegisterSingleton() {
        System.out.println("RegisterSingleton 的构造函数被调用，创建实例中...");
    }

    public static synchronized RegisterSingleton getInstance(String name) {
        if (name == null) {
            name = RegisterSingleton.class.getName();
            System.out.println("name不存在，name = " + name);
        }

        if (registerSingletonMap.get(name) == null) {
            try {
                System.out.println("-->name对应的值不存在，开始创建");
                registerSingletonMap.put(name, (RegisterSingleton) Class.forName(name).newInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("-->name对应的值存在");
        }
        System.out.println("-->返回name对应的值");
        return registerSingletonMap.get(name);
    }

    /**
     * 测试 多线程环境
     * @param args
     */
    public static void main(String[] args) {
        testMultithreaded();
        // System.out.println("=============华丽的分割线=============");
        testExtends();

    }

    private static void testExtends() {
        System.out.println("-----------------登记式单例模式----------------");
        System.out.println("第一次取得实例（登记式）");
        RegisterSingleton s1 = RegisterSingleton.getInstance(null);
        System.out.println(s1);
        System.out.println("第二次取得实例（登记式）");
        RegisterSingletonChildA s3 = RegisterSingletonChildA.getInstance();
        System.out.println(s3);
        System.out.println(s3.about());
        System.out.println("第三次取得实例（登记式）");
        RegisterSingletonChildB s4 = RegisterSingletonChildB.getInstance();
        System.out.println(s4);
        System.out.println(s4.about());
        System.out.println("第四次取得实例（登记式）");
        RegisterSingletonChildB s5 = RegisterSingletonChildB.getInstance();
        System.out.println(s5);
        System.out.println(s5.about());
        System.out.println("第五次取得实例（非正常直接new子类的构造方法）");
        RegisterSingletonChildB s6 = new RegisterSingletonChildB();
        System.out.println(s6);
        System.out.println(s6.about());
        System.out.println("输出父类中Map保存的所有单例，可以看出，直接new出来的实例并没有存在Map中");
        System.out.println(s1.getRegisterSingletonMap());
        System.out.println();
    }

    public static void testMultithreaded() {
        int count = 100;
        CountDownLatch latch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {

            new Thread(() -> {
                RegisterSingleton singleton = RegisterSingleton.getInstance("org.crayzer.design.design_mode_pattern.creational.singleton.register.RegisterSingleton");
                System.out.println(System.currentTimeMillis() + ":" + singleton);
                latch.countDown();
            }).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        RegisterSingleton singleton = new RegisterSingleton();
        System.out.println(singleton.getRegisterSingletonMap());
        RegisterSingletonChildA singleton1 = new RegisterSingletonChildA();
        System.out.println(singleton1.getRegisterSingletonMap());
        RegisterSingletonChildB singleton2 = new RegisterSingletonChildB();
        System.out.println(singleton2.getRegisterSingletonMap());
    }

}
