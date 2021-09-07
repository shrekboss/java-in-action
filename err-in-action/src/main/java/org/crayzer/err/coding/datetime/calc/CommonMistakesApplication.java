package org.crayzer.err.coding.datetime.calc;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;

public class CommonMistakesApplication {

    public static void main(String[] args) throws Exception {
        wrong1();
        wrong1fix();
        right();
        better();
        test();
    }

    // Mon Aug 03 07:04:46 CST 2020
    // Tue Jul 14 14:01:59 CST 2020
    // 得到的日期居然比当前日期还要早，根本不是晚 30 天的时间
    private static void wrong1() {
        System.out.println("wrong1");
        System.out.println("希望得到当前时间之后 30 天的时间:");
        Date today = new Date();
        // 因为 int 发生了溢出 30 -> 30L
        Date nextMonth = new Date(today.getTime() + 30 * 1000 * 60 * 60 * 24);
        System.out.println(today);
        System.out.println(nextMonth);
    }

    private static void wrong1fix() {
        System.out.println("wrong1 fix");
        System.out.println("30 * 1000 * 60 * 60 * 24: " + 30 * 1000 * 60 * 60 * 24 + " " +
                (30L * 1000 * 60 * 60 * 24 > Integer.MAX_VALUE));
        Date today = new Date();
        Date nextMonth = new Date(today.getTime() + 30L * 1000 * 60 * 60 * 24);
        System.out.println(today);
        System.out.println(nextMonth);

    }

    // 手动在时间戳上进行计算操作的方式非常容易出错。对于 Java 8 之前的代码，我更建议使用 Calendar
    private static void right() {
        System.out.println("right");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, 30);
        System.out.println(c.getTime());
    }

    // Java 8 的日期时间类型，可以直接进行各种计算，更加简洁和方便
    private static void better() {
        System.out.println("better");
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.plusDays(30));
    }

    private static void test() {
        System.out.println("//测试操作日期");
        System.out.println(LocalDate.now()
                .minus(Period.ofDays(1))
                .plus(1, ChronoUnit.DAYS)
                .minusMonths(1)
                .plus(Period.ofMonths(1)));

        System.out.println("//计算日期差");
        LocalDate today = LocalDate.of(2019, 12, 12);
        LocalDate specifyDate = LocalDate.of(2019, 10, 1);
        System.out.println(Period.between(specifyDate, today).getDays()); // 11 天
        System.out.println(Period.between(specifyDate, today)); // P2M11D
        System.out.println(ChronoUnit.DAYS.between(specifyDate, today)); // 72 总天数


        System.out.println("//本月的第一天");
        System.out.println(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));

        System.out.println("//今年的程序员日");
        System.out.println(LocalDate.now().with(TemporalAdjusters.firstDayOfYear()).plusDays(255));

        System.out.println("//今天之前的一个周六");
        System.out.println(LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SATURDAY)));

        System.out.println("//本月最后一个工作日");
        System.out.println(LocalDate.now().with(TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY)));

        System.out.println("//自定义逻辑");
        System.out.println(LocalDate.now().with(temporal ->
                temporal.plus(ThreadLocalRandom.current().nextInt(100), ChronoUnit.DAYS)));

        System.out.println("//查询是否是今天要举办生日");
        System.out.println(LocalDate.now().query(CommonMistakesApplication::isFamilyBirthday));

    }

    public static Boolean isFamilyBirthday(TemporalAccessor date) {
        int month = date.get(MONTH_OF_YEAR);
        int day = date.get(DAY_OF_MONTH);

        if (month == Month.FEBRUARY.getValue() && day == 17)
            return Boolean.TRUE;
        if (month == Month.SEPTEMBER.getValue() && day == 21)
            return Boolean.TRUE;
        if (month == Month.MAY.getValue() && day == 22)
            return Boolean.TRUE;
        return Boolean.FALSE;
    }
}

