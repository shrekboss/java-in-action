package org.crayzer.err.coding.serialization.serialversionissue;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    // 实现java.io.Serializable这个接口是为序列化,serialVersionUID 用来表明实现序列化类的不同版本间的
    // 兼容性。如果你修改了此类, 要修改此值。否则以前用老版本的类序列化的类恢复时会出错。
    private static final long serialVersionUID = 3768219943056108047L;
    private String name;
    private int age;
}