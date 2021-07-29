package org.crayzer.design.design_mode_pattern.creational.singleton.prevenSerializeble;

import java.io.*;

public class SerializableSingleton implements Serializable {

    private static final long serialVersionUID = -1994001829338263901L;

    private SerializableSingleton() {
    }

    public final static SerializableSingleton INSTANCE = new SerializableSingleton();

    public static SerializableSingleton getInstance(){
        return INSTANCE;
    }

    /**
    * @method_name: readResolve
    * @describe: 防止 序列化和反序列化后 破坏单例模式规则 启用 readResolve() 方法
    * @author: crayzer
    * @create_date: 2018/3/8
    * @create_time: 下午6:03
    **/
    public  Object readResolve() throws ObjectStreamException{
        return  INSTANCE;
    }

    public static void main(String[] args) {
        SerializableSingleton s1;
        SerializableSingleton s2 = SerializableSingleton.getInstance();
        FileInputStream fis;
        ObjectInputStream ois;

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("serialize.obj");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(s2);
            oos.flush();
            oos.close();

            fis = new FileInputStream("serialize.obj");
            ois = new ObjectInputStream(fis);
            s1 = (SerializableSingleton) ois.readObject();
            ois.close();

            System.out.println(s1);
            System.out.println(s2);
            System.out.println(s1 == s2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
