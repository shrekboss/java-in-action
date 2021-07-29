package org.crayzer.design.design_mode_refactor.idGenerator.orignal;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

public class IdGenerator {
    private static final Logger logger = LoggerFactory.getLogger(IdGenerator.class);

    public static String generate() {
        String id = "";
        try {
            // 修改点：获取 hostName 这部分代码逻辑貌似有点问题，并未处理“hostName 为空”的情况
            String hostName = InetAddress.getLocalHost().getHostName();
            String[] tokens = hostName.split("\\.");
            if (tokens.length > 0) {
                hostName = tokens[tokens.length - 1];
            }
            char[] randomChars = new char[8];
            int count = 0;
            Random random = new Random();
            while (count < 8) {
                // 修改点：极端情况下会随机生成很多三段区间之外的无效数字，需要循环很多次才能生成随机字符串
                int randomAscii = random.nextInt(122);
                // 修改点：多个 if 语句
                if (randomAscii >= 48 && randomAscii <= 57) {
                    randomChars[count] = (char)('0' + (randomAscii - 48));
                    count++;
                } else if (randomAscii >= 65 && randomAscii <= 90) {
                    randomChars[count] = (char)('A' + (randomAscii - 65));
                    count++;
                } else if (randomAscii >= 97 && randomAscii <= 122) {
                    randomChars[count] = (char)('a' + (randomAscii - 97));
                    count++;
                }
            }
            id = String.format("%s-%d-%s", hostName,
                    System.currentTimeMillis(), new String(randomChars));
        } catch (UnknownHostException e) {
            // 修改点：对异常的处理是在 IdGenerator 内部将其吐掉，然后打印一条报警日志，并没有继续往上抛出。
            // 这样的异常处理是否得当呢？
            logger.warn("Failed to get the host name.", e);
        }

        return id;
    }

    public static void main(String[] args) {
        System.out.println(IdGenerator.generate());
        System.out.println(IdGenerator.generate());
        System.out.println(IdGenerator.generate());
    }
}
