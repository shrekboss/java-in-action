package org.crayzer.jvm.opcode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DemoListRemove {
    public static void main(String[] args) {
        List<String> nameList = new ArrayList<>();
        nameList.add("kk");
        nameList.add("stu");
        nameList.add("cuicui");
        nameList.add("maomao");
        for (String name : nameList) {
            if (name.contains("s")) {
                // java.util.ConcurrentModificationException
                nameList.remove(name);
            }
        }
        nameList = nameList.stream()
                .filter(i -> !i.contains("s"))
                .filter(i -> !i.contains("s"))
                .collect(Collectors.toList());
        System.out.println(nameList);
    }
}
