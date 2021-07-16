package org.crayzer.conc.design.pattern.threadlocal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadLocalDemo {

    public static void main(String[] args) {
        ThreadId.get();
        ThreadId.get();
        new Thread(() -> ThreadId.get()).start();
        new Thread(() -> ThreadId.get()).start();

        SafeDateFormat.get();
        SafeDateFormat.get();
        new Thread(() -> SafeDateFormat.get()).start();

        new Thread(() -> {
            DateFormat dateFormat = SafeDateFormat.get();
            System.out.println(Thread.currentThread().getId());
            System.out.println(dateFormat.format(new Date()));
        }).start();

        new Thread(() -> {
            DateFormat dateFormat = SafeDateFormat.get();
            System.out.println(Thread.currentThread().getId());
            System.out.println(dateFormat.format(new Date()));
        }).start();
    }

    static class ThreadId {

        static final AtomicLong nextId = new AtomicLong(0);
        static final ThreadLocal<Long> tl = ThreadLocal.withInitial(
                () -> nextId.getAndIncrement()
        );

        static Long get() {
            System.out.println(tl);
            return tl.get();
        }
    }


    static class SafeDateFormat {
        //定义ThreadLocal变量
        static final ThreadLocal<DateFormat>
                tl = ThreadLocal.withInitial(
                () -> new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss"));

        static DateFormat get() {
            System.out.println(tl.get());
            return tl.get();
        }
    }
}
