package org.crayzer.jvm.classloader;

//import org.example.HelloDemo;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * javac -g jvm-in-action/src/main/java/org/crayzer/jvm/classloader/HelloDemo.java
 * <p/>
 * javap -c -verbose jvm-in-action/target/classes/org/crayzer/jvm/bytecode/HelloByteCode
 * <p/>
 * 添加引用类的几种方式：
 * <p/>
 * 1. 放到 JDK 的lib/ext 下，或者 -Djava.ext.dirs
 * <p/>
 * 2. java -cp/classpath 或者 class 文件放到当前路径
 * <p/>
 * 3. 自定义 CLassLoader 加载
 * <p/>
 * 4. 拿到当前执行类的 ClassLoader，放射调用 addUrl 方法添加 Jar 或者路径(jdk9 无效)
 */
public class JvmAppClassLoaderAddURL {
    public static void main(String[] args) {
        String appPath = "file:/Users/yizhe.chen/workspace/github/java-in-action/jvm-in-action/jar-project-1.0-SNAPSHOT.jar";
        URLClassLoader urlClassLoader = (URLClassLoader) JvmAppClassLoaderAddURL.class.getClassLoader();

        try {
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            URL url = new URL(appPath);
            method.invoke(urlClassLoader, url);

//            URL[] urLs = urlClassLoader.getURLs();
//            for (URL u : urLs) {
//                System.out.println(u);
//            }

            Class.forName("org.example.HelloDemo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
