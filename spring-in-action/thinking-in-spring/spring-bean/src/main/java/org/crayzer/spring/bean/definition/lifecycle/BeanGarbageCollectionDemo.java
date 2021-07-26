package org.crayzer.spring.bean.definition.lifecycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Bean 垃圾回收（GC）示例
 *
 * @author Crayzer
 */
public class BeanGarbageCollectionDemo {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanInitializationDemo.class);
        applicationContext.refresh();
        applicationContext.close();

        Thread.sleep(5000L);
        System.gc();
        Thread.sleep(5000L);
    }
}
