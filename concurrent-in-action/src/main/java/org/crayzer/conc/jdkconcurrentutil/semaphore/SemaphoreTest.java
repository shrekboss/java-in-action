package org.crayzer.conc.jdkconcurrentutil.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 基于 {@link Semaphore} 实现流量控制 - 学生食堂
 */
public class SemaphoreTest {
    private static Semaphore s = new Semaphore(1);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Student(s, "学生" + i).start();
        }
    }

    private static class Student extends Thread {
        private Semaphore s;
        private String name;

        public Student(Semaphore s, String name) {
            this.s = s;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                s.acquire();
                System.out.println(name + " 拿到了打饭的许可");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(name + " 打好了饭，释放这个窗口");
                s.release();
            }
        }
    }
}
