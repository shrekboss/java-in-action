package org.crayzer.jvm.opcode;

import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;

import java.util.HashSet;
import java.util.Set;

public class TestEqualsHashCode {
    private String name;

    TestEqualsHashCode(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TestEqualsHashCode)) return false;
        if (this == obj) return true;
        TestEqualsHashCode other = (TestEqualsHashCode) obj;
        return (name == null && null == other.name)
                || (null != name && name.equals(other.name));
    }

    @Override
    public int hashCode() {
        return (int) (Math.random() * 2021);
    }

    @Override
    public String toString() {
        return "TestEqualsHashCode{name=" + name + "}";
    }
}

class Test {
    public static void main(String[] args) {
        Set<TestEqualsHashCode> itemSet = new HashSet<>();
        TestEqualsHashCode java = new TestEqualsHashCode("java");
        TestEqualsHashCode jvm = new TestEqualsHashCode("jvm");
        itemSet.add(java);
        itemSet.add(jvm);
        itemSet.add(jvm);
        // 大概率是 false
        System.out.println(itemSet.contains(jvm));
        System.out.println(itemSet);

        StatsDClient dataDogClient = new NonBlockingStatsDClient("huobi.rc", "dd-agent-1a-2.aws-jp1.huobiidc.com", 8125, "app:rctask" , "env:prd");

        dataDogClient.count("rctask",1,"url:");
    }
}
