package org.crayzer.jvm.opcode;

import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

public final class TestList2Map {

    public static void main(String[] args) {
        List<Pair<String, String>> pairList1 = new ArrayList<>();
        List<Pair<String, String>> pairList2 = new ArrayList<>();
        // List转Map
        Map<String, Pair<String, String>> map1 = pairList1.stream()
                .collect(Collectors.toMap(i -> i.getKey(), i -> i));
        //
        for (Pair<String, String> pair2 : pairList2) {
            String key = pair2.getKey();
            Pair<String, String> pair1 = map1.get(key);
            if (pair2.getValue().equalsIgnoreCase(pair1.getValue())) {
                System.out.println("匹配:" + key + ";" + pair1 + ";" + pair2);
            }
        }
    }
}
