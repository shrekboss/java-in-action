package org.crayzer.jvm.opcode;

import java.util.*;

public class TestOptional {

    public static String getName(int id) {
        if (id > 0) {
            return "user-" + id;
        }
        return null;
    }
    public static Optional<String> loadName(int id) {
        if (id > 0) {
            return Optional.ofNullable(getName(id));
        }
        return Optional.empty();
    }
    public static void main(String[] args) {
        int id = new Random().nextInt(2);
        String theName = getName(id);
        if (null != theName) {
            System.out.println("theName =" + theName);
        }
        //
        Optional<String> theNameOptional = loadName(id);
        if (theNameOptional.isPresent()) {
            System.out.println("get.name=" + theNameOptional.get());
        }
        System.out.println("orElse=" + theNameOptional.orElse("unknown"));
        theNameOptional.filter(Objects::nonNull).map(i -> i.toUpperCase())
                .ifPresent(System.out::println);
    }
}
