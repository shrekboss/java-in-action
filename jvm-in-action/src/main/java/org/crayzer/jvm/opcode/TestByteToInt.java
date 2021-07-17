package org.crayzer.jvm.opcode;

public class TestByteToInt {
    public static void main(String[] args) {
        // -128
        System.out.println(Byte.MIN_VALUE);
        // 127
        System.out.println(Byte.MAX_VALUE);
        // false; 有符号扩展;
        System.out.println(0x80 == (byte) 0x80);
        // 128
        System.out.println(0x80);
        // -128
        System.out.println((byte) 0x80);
        // 0
        System.out.println(1024 * 1024 * 1024 * 4);
    }
}
