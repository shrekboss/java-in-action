package org.crayzer.jvm.opcode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class TestSimpleDateFormat {
    private static final SimpleDateFormat format
            = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // 错误计数器
    private static AtomicInteger errorCounter = new AtomicInteger(0);
    public static void main(String[] args) throws ParseException {
        String str1 = "2021-01-17 19:30:30";
        Date date1 = format.parse(str1);
        String str2 = "2021-01-18 19:30:30";
        Date date2 = format.parse(str2);
        long timeoutMillis = System.currentTimeMillis() + 1000;
        IntStream.range(1, 9).parallel()
                .forEach(i -> {
                    String expect = (i % 2 == 0) ? str1 : str2;
                    Date d = (i % 2 == 0) ? date1 : date2;
                    while (timeoutMillis > System.currentTimeMillis()) {
                        String cur = format.format(d);
                        if (!expect.equals(cur)) {
                            errorCounter.incrementAndGet();
                        }
                    }
                });
        // errorCounter=660972
        System.out.println("errorCounter=" + errorCounter.get());
    }
}
