package org.crayzer.jvm.opcode;

import java.time.ZoneOffset;
import java.util.Date;

public class TestDateTime {

    public static void main(String... args) {
        Date current = new Date(System.currentTimeMillis());
        System.out.println(current);
        System.out.println(current.getTime());
        System.out.println(current.toInstant());
        System.out.println(current.toInstant().atOffset(ZoneOffset.of("+8")));
        System.out.println(current.toInstant().atOffset(ZoneOffset.of("-8")));
    }
}
