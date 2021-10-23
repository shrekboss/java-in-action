package org.crayzer.err;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 * @since 1.0.0
 */
public class TestCompareFields {
    public static void main(String[] args) {
        Student s1 = new Student("1", "test1", 13214, "shenzhen");
        Student s2 = new Student("2", "test2", 123, "珠海");
        //比较S1 和 S2 的属性值，其中id忽略比较
        Map<String, List<Object>> compareResult = compareFields(s1, s2, new String[]{"id"});
        Set<String> keySet = compareResult.keySet();
        for (String key : keySet) {
            List<Object> list = compareResult.get(key);
            System.out.println(">>>  s1的" + key + "为" + list.get(0) + "，s2的" + key + "为" + list.get(1));
        }
    }

    /**
     * @param obj1      进行属性比较的对象1
     * @param obj2      进行属性比较的对象2
     * @param ignoreArr 选择忽略比较的属性数组
     * @return 属性差异比较结果 map
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, List<Object>> compareFields(Object obj1, Object obj2, String[] ignoreArr) {
        try {
            Map<String, List<Object>> map = new HashMap<>();
            List<String> ignoreList = null;
            if (ignoreArr != null && ignoreArr.length > 0) {
                ignoreList = Arrays.asList(ignoreArr);
            }
            if (obj1.getClass() == obj2.getClass()) { //只有两个对象都是同一类型才有可比性
                Class clazz = obj1.getClass();
                //获取object的属性描述
                PropertyDescriptor[] pds = Introspector.getBeanInfo(clazz, Object.class).getPropertyDescriptors();
                for (PropertyDescriptor pd : pds) {
                    String name = pd.getName();
                    if (ignoreList != null && ignoreList.contains(name)) {
                        continue;
                    }
                    Method readMethod = pd.getReadMethod();//获取属性的get方法
                    //在obj1上调用get方法等同于获得obj1的属性值
                    Object o1 = readMethod.invoke(obj1);
                    //在obj2上调用get方法等同于获得obj2的属性值
                    Object o2 = readMethod.invoke(obj2);
                    //如果此属性是时间
                    if (o1 instanceof Timestamp) {
                        o1 = new Date(((Timestamp) o1).getTime());
                    }
                    if (o2 instanceof Timestamp) {
                        o2 = new Date(((Timestamp) o2).getTime());
                    }
                    if (o1 == null && o2 == null) {
                        continue;
                    } else if (o1 == null && o2 != null) {
                        //不一致
                        List<Object> list = new ArrayList<Object>();
                        list.add(o1);
                        list.add(o2);
                        map.put(name, list);
                        continue;
                    }
                    if (!o1.equals(o2)) {
                        List<Object> list = new ArrayList<>();
                        list.add(o1);
                        list.add(o2);
                        map.put(name, list);
                    }
                }
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

