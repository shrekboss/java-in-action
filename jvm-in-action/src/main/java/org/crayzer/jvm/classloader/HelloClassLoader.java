package org.crayzer.jvm.classloader;

import java.util.Base64;

/**
 * javac -g jvm-in-action/src/main/java/org/crayzer/jvm/classloader/Hello.java
 * <p/>
 * base64 jvm-in-action/src/main/java/org/crayzer/jvm/classloader/Hello.class
 */
public class HelloClassLoader extends ClassLoader{

    public static void main(String[] args) {
        try {
            new HelloClassLoader().findClass("org.crayzer.jvm.classloader.Hello").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String helloBase64 = "yv66vgAAADQAHwoABgARCQASABMIABQKABUAFgcAFwcAGAE" +
                "ABjxpbml0PgEAAygpVgEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBABJMb2Nh" +
                "bFZhcmlhYmxlVGFibGUBAAR0aGlzAQAjTG9yZy9jcmF5emVyL2p2bS9jbGFzc2xvY" +
                "WRlci9IZWxsbzsBAAg8Y2xpbml0PgEAClNvdXJjZUZpbGUBAApIZWxsby5qYXZh" +
                "DAAHAAgHABkMABoAGwEAGEhlbGxvIENsYXNzIEluaXRpYWxpemVkIQcAHAwAHQ" +
                "AeAQAhb3JnL2NyYXl6ZXIvanZtL2NsYXNzbG9hZGVyL0hlbGxvAQAQamF2YS9sY" +
                "W5nL09iamVjdAEAEGphdmEvbGFuZy9TeXN0ZW0BAANvdXQBABVMamF2YS9pb" +
                "y9QcmludFN0cmVhbTsBABNqYXZhL2lvL1ByaW50U3RyZWFtAQAHcHJpbnRsbgE" +
                "AFShMamF2YS9sYW5nL1N0cmluZzspVgAhAAUABgAAAAAAAgABAAcACAABAAkA" +
                "AAAvAAEAAQAAAAUqtwABsQAAAAIACgAAAAYAAQAAAAMACwAAAAwAAQAAA" +
                "AUADAANAAAACAAOAAgAAQAJAAAAJQACAAAAAAAJsgACEgO2AASxAAAAAQ" +
                "AKAAAACgACAAAABQAIAAYAAQAPAAAAAgAQ";
        byte[] bytes = decode(helloBase64);
        return defineClass(name, bytes, 0, bytes.length);
    }

    private byte[] decode(String base64) {
        return Base64.getDecoder().decode(base64);
    }
}
