package org.crayzer.jvm.classloader;

//import org.example.HelloDemo;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * javac -g jvm-in-action/src/main/java/org/crayzer/jvm/classloader/HelloDemo.java
 * <p/>
 * javap -c -verbose jvm-in-action/target/classes/org/crayzer/jvm/bytecode/HelloByteCode
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
