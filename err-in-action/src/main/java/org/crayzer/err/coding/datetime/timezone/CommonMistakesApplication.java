package org.crayzer.err.coding.datetime.timezone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class CommonMistakesApplication {

    public static void main(String[] args) throws Exception {
        test();
        wrong1();
        wrong2();
        right();
    }

    /**
     * Thu Jan 01 08:00:00 CST 1970
     * Asia/Shanghai:8
     */
    private static void test() {
        System.out.println("test");
        System.out.println(new Date(0));
        System.out.println(TimeZone.getDefault().getID() + ":" + TimeZone.getDefault().getRawOffset() / 3600 / 1000);
        // ZoneId.getAvailableZoneIds().forEach(id -> System.out.println(String.format("%s:%s", id, ZonedDateTime.now(ZoneId.of(id)))));
    }

    /**
     * 对于同一个时间表示，比如 2020-01-02 22:00:00，不同时区的人转换成 Date 会得到不同的时间（时间戳）：这正是 UTC 的意义，并不是时间错乱。
     * <p/>
     * Thu Jan 02 22:00:00 CST 2020:1577973600000
     * Fri Jan 03 11:00:00 CST 2020:1578020400000
     */
    private static void wrong1() throws ParseException {
        System.out.println("wrong1");
        String stringDate = "2020-01-02 22:00:00";
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = inputFormat.parse(stringDate);
        System.out.println(date1 + ":" + date1.getTime());
        inputFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        Date date2 = inputFormat.parse(stringDate);
        System.out.println(date2 + ":" + date2.getTime());
    }

    // 格式化后出现的错乱，即同一个 Date，在不同的时区下格式化得到不同的时间表示。
    // 有些时候数据库中相同的时间，由于服务器的时区设置不同，读取到的时间表示不同。这，不是时间错
    // 乱，正是时区发挥了作用，因为 UTC 时间需要根据当前时区解析为正确的本地时间。

    /**
     * 格式化后出现的错乱，即同一个 Date，在不同的时区下格式化得到不同的时间表示。
     * 有些时候数据库中相同的时间，由于服务器的时区设置不同，读取到的时间表示不同。这，不是时间错
     * 乱，正是时区发挥了作用，因为 UTC 时间需要根据当前时区解析为正确的本地时间。
     * <p/>
     * [2020-01-02 22:00:00 +0800]
     * [2020-01-02 09:00:00 -0500]
     */
    private static void wrong2() throws ParseException {
        System.out.println("wrong2");
        String stringDate = "2020-01-02 22:00:00";
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = inputFormat.parse(stringDate);
        // 当前时区的 Offset（时差）是 +8 小时，对于 -5 小时的纽约，晚上 10 点对应早上 9 点：
        System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss Z]").format(date));
        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
        System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss Z]").format(date));
    }

    /**
     * Asia/Shanghai2020-01-02 21:00:00 +0800
     * America/New_York2020-01-02 08:00:00 -0500
     * +09:002020-01-02 22:00:00 +0900
     */
    private static void right() {
        System.out.println("right");

        String stringDate = "2020-01-02 22:00:00";
        ZoneId timeZoneSH = ZoneId.of("Asia/Shanghai");
        ZoneId timeZoneNY = ZoneId.of("America/New_York");
        ZoneId timeZoneJST = ZoneOffset.ofHours(9);// 东京

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 存的时候，需要使用正确的当前时区来保存，这样 UTC 时间才会正确；
        ZonedDateTime date = ZonedDateTime.of(LocalDateTime.parse(stringDate, dateTimeFormatter), timeZoneJST);

        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
        // 读的时候，也只有正确设置本地时区，才能把 UTC 时间转换为正确的当地时间。
        System.out.println(timeZoneSH.getId() + outputFormat.withZone(timeZoneSH).format(date));
        System.out.println(timeZoneNY.getId() + outputFormat.withZone(timeZoneNY).format(date));
        System.out.println(timeZoneJST.getId() + outputFormat.withZone(timeZoneJST).format(date));
    }
}

