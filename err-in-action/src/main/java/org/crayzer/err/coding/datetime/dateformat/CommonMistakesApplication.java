package org.crayzer.err.coding.datetime.dateformat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CommonMistakesApplication {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static ThreadLocal<SimpleDateFormat> threadSafeSimpleDateFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    // 不用去记忆使用大写的 Y 还是小写的 Y，大写的 M 还是小写的 m
    // DateTimeFormatter 线程安全
    // DateTimeFormatter 的解析比较严格，需要解析的字符串和格式不匹配时，会直接报错，而不会把
    // 0901 解析为月份
    private static DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.YEAR)
            .appendLiteral("/")
            .appendValue(ChronoField.MONTH_OF_YEAR)
            .appendLiteral("/")
            .appendValue(ChronoField.DAY_OF_MONTH)
            .appendLiteral(" ")
            .appendValue(ChronoField.HOUR_OF_DAY)
            .appendLiteral(":")
            .appendValue(ChronoField.MINUTE_OF_HOUR)
            .appendLiteral(":")
            .appendValue(ChronoField.SECOND_OF_MINUTE)
            .appendLiteral(".")
            .appendValue(ChronoField.MILLI_OF_SECOND)
            .toFormatter();

    public static void main(String[] args) throws Exception {
        System.out.println("===============test===============");
        // test();

        System.out.println("===============wrong1===============");
        // wrong1();
        System.out.println("===============wrong1fix===============");
        // wrong1fix();
        System.out.println("===============better===============");
        // better();

        // SimpleDateFormat parse | format 线程非安全
        System.out.println("===============wrong2===============");
        // wrong2();
        System.out.println("===============wrong2fix===============");
        // wrong2fix();

        // dateTimeFormatter 测试
        System.out.println("===============dateTimeFormatterDemo===============");
        dateTimeFormatterDemo();
    }

    private static void test() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        System.out.println(simpleDateFormat.format(calendar.getTime()));
        System.out.println(dateTimeFormatter.format(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())));
        System.out.println(dateTimeFormatter.format(LocalDateTime.now()));
    }

    // 这明明是一个 2019 年的日期，怎么使用 SimpleDateFormat 格式化后就提前跨年了
    // 小写 y 是年，而大写 Y 是 week year，也就是所在的周属于哪一年

    // 一年第一周的判断方式是，从 getFirstDayOfWeek() 开始，完整的 7 天，并且包含那一年至少
    // getMinimalDaysInFirstWeek() 天。这个计算方式和区域相关。
    private static void wrong1() throws ParseException {
        //三个问题，YYYY、线程不变安全、不合法格式

        // 对于当前 zh_CN 区域来说，2020 年第一周的条件是，从周日开始的完整 7 天，2020 年包含 1
        // 天即可。显然，2019 年 12 月 29 日周日到 2020 年 1 月 4 日周六是 2020 年第一周，得出的
        // week year 就是 2020 年。
        Locale.setDefault(Locale.SIMPLIFIED_CHINESE);

        // 那么 week yeay 就还是 2019 年，因为一周的第一天从周一开始算，2020 年的第一周是 2019 年
        // 12 月 30 日周一开始，29 日还是属于去年：
        // Locale.setDefault(Locale.FRANCE);

        System.out.println("defaultLocale:" + Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.DECEMBER, 29, 0, 0, 0);
        SimpleDateFormat YYYY = new SimpleDateFormat("YYYY-MM-dd");
        System.out.println("格式化: " + YYYY.format(calendar.getTime()));
        System.out.println("weekYear:" + calendar.getWeekYear());
        System.out.println("firstDayOfWeek:" + calendar.getFirstDayOfWeek());
        System.out.println("minimalDaysInFirstWeek:" + calendar.getMinimalDaysInFirstWeek());

        String dateString = "20160901";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
        // result:Mon Jan 01 00:00:00 CST 2091
        // 把 0901 当成了月份，相当于 75 年, 75 + 2016 = 2091
        System.out.println("result:" + dateFormat.parse(dateString));

    }

    // 没有特殊需求，针对年份的日期格式化，应该一律使用 “y” 而非 “Y”
    private static void wrong1fix() throws ParseException {
        SimpleDateFormat yyyy = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.DECEMBER, 29, 0, 0, 0);
        // calendar.set(2019, Calendar.DECEMBER, 29, 23, 24, 25);
        System.out.println("格式化: " + yyyy.format(calendar.getTime()));
        System.out.println("weekYear:" + calendar.getWeekYear());
        System.out.println("firstDayOfWeek:" + calendar.getFirstDayOfWeek());
        System.out.println("minimalDaysInFirstWeek:" + calendar.getMinimalDaysInFirstWeek());

        String dateString = "20160901";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        // result:Thu Sep 01 00:00:00 CST 2016
        System.out.println("result:" + dateFormat.parse(dateString));
    }

    private static void better() {
        LocalDateTime localDateTime = LocalDateTime.parse("2020/1/2 12:34:56.789", dateTimeFormatter);
        System.out.println(localDateTime.format(dateTimeFormatter));

        String dt = "20160901";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMM");
        System.out.println("result:" + dateTimeFormatter.parse(dt));
    }

    // SimpleDateFormat 的作用是定义解析和格式化日期时间的模式，但它的解析和格式化操作是非线程
    // 安全的。
    private static void wrong2() throws InterruptedException {

        ExecutorService threadPool = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 20; i++) {
            //提交20个并发解析时间的任务到线程池，模拟并发环境
            threadPool.execute(() -> {
                for (int j = 0; j < 10; j++) {
                    try {
                        System.out.println(simpleDateFormat.parse("2020-01-01 11:12:13"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }

    private static void wrong2fix() throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 20; i++) {
            threadPool.execute(() -> {
                for (int j = 0; j < 10; j++) {
                    try {
                        System.out.println(threadSafeSimpleDateFormat.get().parse("2020-01-01 11:12:13"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }

    private static void dateTimeFormatterDemo() {
        //使用刚才定义的DateTimeFormatterBuilder构建的DateTimeFormatter来解析这个时间
        LocalDateTime localDateTime = LocalDateTime.parse("2020/1/2 12:34:56.789", dateTimeFormatter);
        //解析成功
        System.out.println(localDateTime.format(dateTimeFormatter));
        //使用yyyyMM格式解析20160901是否可以成功呢？
        String dt = "20160901";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMM");
        System.out.println("result:" + dateTimeFormatter.parse(dt));
    }
}

