package org.crayzer.jvm;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * java 没设置 -classpath / -cp，默认从当前目录（也就是你执行 java 命令的目录）找 class 文件，
 * 当你执行的类中存在 package ，JVM 会从当前目录，顺着你的 package 目录找下，这时候就
 * 找不到了，因为你当前目录没有 package 所指定目录及该类
 *
 * <p/>
 * cd /Users/yizhe.chen/workspace/github/java-in-action/jvm-in-action/target/classes
 * <p/>
 * java -XX:+UseSerialGC -Xms512m -Xmx512m -Xloggc:gc.serial.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
 * <p/>
 * java -XX:+UseParallelGC -Xms512m -Xmx512m -Xloggc:gc.parallel.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
 * <p/>
 * java -XX:+UseConcMarkSweepGC -Xms512m -Xmx512m -Xloggc:gc.concMarkSweep.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
 * <p/>
 * java -XX:+UseG1GC -Xms512m -Xmx512m -Xloggc:gc.g1.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
 */
public class GCLogAnalysis {

    private static Random random = new Random();

    public static void main(String[] args) {
        long startMillis = System.currentTimeMillis();
        long timeoutMillis = TimeUnit.SECONDS.toMillis(1);
        long endMillis = startMillis + timeoutMillis;

        LongAdder counter = new LongAdder();
        System.out.println("正在执行...");

        int cacheSize = 2000;
        Object[] cacheGarbage = new Object[cacheSize];

        while (System.currentTimeMillis() < endMillis) {
            Object garbage = generateGarbage(100 * 1024);
            counter.increment();
            int randomIndex = random.nextInt(2 * cacheSize);
            if (randomIndex < cacheSize) {
                cacheGarbage[randomIndex] = garbage;
            }
        }
        System.out.println("执行结束！共产生对象次数：" + counter.longValue());
    }

    private static Object generateGarbage(int max) {
        int randomSize = random.nextInt(max);
        int type = randomSize % 4;
        Object result;

        switch (type) {
            case  0:
                result = new int[randomSize];
                break;
            case 1:
                result = new byte[randomSize];
                break;
            case 2:
                result = new double[randomSize];
                break;
            default:
                StringBuilder builder = new StringBuilder();
                String randomString = "randomString-Anything";
                while (builder.length() < randomSize) {
                    builder.append(randomString);
                    builder.append(max);
                    builder.append(randomSize);
                }
                result = builder.toString();
                break;
        }
        return result;
    }
}
