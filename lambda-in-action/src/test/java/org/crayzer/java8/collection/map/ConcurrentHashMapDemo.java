package org.crayzer.java8.collection.map;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yizhe.chen
 */
public class ConcurrentHashMapDemo {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");
        System.out.println(map);

        String[] strings = new String[2];
        strings[0] = "0";
        strings[1] = "1";
    }
}


