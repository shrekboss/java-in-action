package org.crayzer.generate.data;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
public class HandleMaxRepeatProblem_v0 {

    public static final int start = 18;
    public static final int end = 70;

    public static final String dir = "dataDir";

    public static final String FILE_NAME = "User.dat";


    /**
     * 统计数量
     */
    private static Map<String, AtomicInteger> countMap = new ConcurrentHashMap<>();


    /**
     * 开启消费的标志
     */
    private static volatile boolean startConsumer = false;

    /**
     * 消费者运行保证
     */
    private static volatile boolean consumerRunning = true;


    /**
     * 按照 "," 分割数据，并写入到countMap里
     */
    static class SplitData {

        public static void splitLine(String lineData) {
            String[] arr = lineData.split(",");
            for (String str : arr) {
                if (StringUtils.isEmpty(str)) {
                    continue;
                }
                countMap.computeIfAbsent(str, s -> new AtomicInteger(0)).getAndIncrement();
            }
        }


    }

    /**
     *  init map
     */

    static {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdir();
        }


        for (int i = start; i <= end; i++) {
            try {
                File subFile = new File(dir + "/" + i + ".dat");
                if (!file.exists()) {
                    subFile.createNewFile();
                }
                countMap.computeIfAbsent(i + "", integer -> new AtomicInteger(0));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {


        new Thread(() -> {
            try {
                readData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


    }


    private static void readData() throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_NAME), "utf-8"));
        String line;
        long start = System.currentTimeMillis();
        int count = 1;
        while ((line = br.readLine()) != null) {
            // 按行读取，并向map里写入数据
            SplitData.splitLine(line);
            if (count % 100 == 0) {
                System.out.println("读取100行,总耗时间: " + (System.currentTimeMillis() - start) / 1000 + " s");
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            count++;
        }
        findMostAge();

        br.close();
    }

    private static void findMostAge() {
        Integer targetValue = 0;
        String targetKey = null;
        Iterator<Map.Entry<String, AtomicInteger>> entrySetIterator = countMap.entrySet().iterator();
        while (entrySetIterator.hasNext()) {
            Map.Entry<String, AtomicInteger> entry = entrySetIterator.next();
            Integer value = entry.getValue().get();
            String key = entry.getKey();
            if (value > targetValue) {
                targetValue = value;
                targetKey = key;
            }
        }
        System.out.println("数量最多的年龄为:" + targetKey + "数量为：" + targetValue);
    }

    private static void clearTask() {
        // 清理，同时找出出现字符最大的数
        findMostAge();
        System.exit(-1);
    }

}
