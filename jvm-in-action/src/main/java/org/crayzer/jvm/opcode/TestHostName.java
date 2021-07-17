package org.crayzer.jvm.opcode;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class TestHostName {
    public static void main(String[] args) {
        // 运行时信息; 可以获取各种环境信息
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        String vmId = runtimeMXBean.getName();
        String[] vmInfos = vmId.split("@");
        String processId = vmInfos[0]; // 进程id
        // long pid = runtimeMXBean.getPid(); // JDK10;
        String hostname = vmInfos[1];  // hostname
        System.out.println("vmId=" + vmId);
        System.out.println("processId=" + processId);
        System.out.println("hostname=" + hostname);
        //
        Runtime runtime = Runtime.getRuntime();
        System.out.println("CPU数量=" + runtime.availableProcessors());
        System.out.println("JVM最大内存=" + runtime.maxMemory());
        System.out.println("JVM当前内存=" + runtime.totalMemory());
        System.out.println("JVM空闲内存=" + runtime.freeMemory());
    }
}
