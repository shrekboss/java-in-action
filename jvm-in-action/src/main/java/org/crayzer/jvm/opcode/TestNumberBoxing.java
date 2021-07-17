package org.crayzer.jvm.opcode;

public class TestNumberBoxing{

    public static void main(String[] args) {
        Integer num1 = null;
        eq0(num1);
    }

    private static boolean eq0(Integer num) {
        return 0 == num;
    }
}
