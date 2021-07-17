package org.crayzer.jvm.opcode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 演示invoke操作码
 */
public class DemoInvokeOpcode {

    public static void testMethodInvoke() {
        // 183; invokespecial
        HashMap<String, String> hashMap = new HashMap<String, String>(100);
        // 182; invokevirtual
        hashMap.put("name", "tiemao");
        // 赋值给Map接口引用
        Map<String, String> map = hashMap;
        // 185; invokeinterface
        map.putIfAbsent("url", "https://renfufei.blog.csdn.net");
        // 使用lambda
        List<String> upperKeys = map.keySet().stream()
                // invokedynamic
                .map(i -> i.toUpperCase())
                .collect(Collectors.toList());
        // 184; invokestatic
        String str = String.valueOf(upperKeys);
        // 183; invokespecial
        System.out.println(str);
    }

    public static void main(String[] args) {
        // 184; invokestatic
        testMethodInvoke();
    }
}
