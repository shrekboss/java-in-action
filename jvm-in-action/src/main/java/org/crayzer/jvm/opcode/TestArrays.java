package org.crayzer.jvm.opcode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TestArrays {

    public static void main(String... args) throws IOException {
        List<String> names = Arrays.asList("maomoa", "cuicui", "kk");
        List<Integer> ages = Arrays.asList(18, 18, 18);
        //
        TestSystem.out.println(names);
        TestSystem.out.println(ages);
    }
}
