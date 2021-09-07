package org.crayzer.err.coding.datetime.newdate;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CommonMistakesApplication {

    public static void main(String[] args) throws Exception {
        wrong();
        wrongfix();
        right();
        better();
    }

    /**
     * Sat Jan 31 11:12:13 CST 3920
     */
    private static void wrong() {
        System.out.println("wrong");
        // 低级错误：年应该是和 1900 的差值，月应该是从 0 到 11 而不是从 1 到 12
        Date date = new Date(2019, 12, 31, 11, 12, 13);
        System.out.println(date);
    }

    /**
     * Tue Dec 31 11:12:13 CST 2019
     */
    private static void wrongfix() {
        System.out.println("wrongfix");
        Date date = new Date(2019 - 1900, 11, 31, 11, 12, 13);
        System.out.println(date);
    }

    /**
     * Tue Dec 31 11:12:13 CST 2019
     * Wed Jan 01 00:12:13 CST 2020
     */
    private static void right() {
        System.out.println("right");
        Calendar calendar = Calendar.getInstance();
        // 月需要注意是从 0 到 11
        calendar.set(2019, 11, 31, 11, 12, 13);
        System.out.println(calendar.getTime());
        Calendar calendar2 = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
        calendar2.set(2019, Calendar.DECEMBER, 31, 11, 12, 13);
        System.out.println(calendar2.getTime());
    }

    /**
     * 2019-12-31T11:12:13
     * 2019-12-31T11:12:13-05:00[America/New_York]
     */
    private static void better() {
        System.out.println("better");
        LocalDateTime localDateTime = LocalDateTime.of(2019, Month.DECEMBER, 31, 11, 12, 13);
        System.out.println(localDateTime);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("America/New_York"));
        System.out.println(zonedDateTime);
    }
}

