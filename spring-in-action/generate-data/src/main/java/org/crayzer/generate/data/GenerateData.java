package org.crayzer.generate.data;

import java.io.*;
import java.util.Random;

/**
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
public class GenerateData {

    private static Random random = new Random();


    public static int generateRandomData(int start, int end) {
        return random.nextInt(end - start + 1) + start;
    }


    /**
     * 产生10G的 1-1000的数据
     */
    public void generateData() throws IOException {
        File file = new File("User.dat");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int start = 18;
        int end = 70;
        long startTime = System.currentTimeMillis();
        BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
        for (long i = 1; i < Integer.MAX_VALUE * 1.7; i++) {
            String data = generateRandomData(start, end) + ",";
            bos.write(data);
            // 每100万条记录成一行，100万条数据大概4M
            if (i % 1000000 == 0) {
                bos.write("\n");
            }
        }
        System.out.println("写入完成! 共花费时间:" + (System.currentTimeMillis() - startTime) / 1000 + " s");
        bos.close();
    }


    public static void main(String[] args) {
        GenerateData generateData = new GenerateData();
        try {
            generateData.generateData();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
