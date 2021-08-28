package org.crayzer.java8.collection.map;

import java.util.HashMap;

/**
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 */
public class MapDemo extends HashMap<String, String>{

    public static void main(String[] args) {

        HashMap<String, String> map = new HashMap<>();
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");
        System.out.println(map);

        /* HashMap 扩容逻辑 */
        // map.resize();

        /* 添加数值*/
        map.put("4", "d");

        /* 树化 */
        // map.treeifyBin(null, 0);

        /** 数组长度为 素数 和 合数 的区别 */
        System.out.println(1 % 4);
        System.out.println(3 % 4);
        System.out.println(5 % 4);
        System.out.println(7 % 4);
        System.out.println("=====华丽的分割线=====");
        System.out.println(1 % 5);
        System.out.println(3 % 5);
        System.out.println(5 % 5);
        System.out.println(7 % 5);
    }
}
