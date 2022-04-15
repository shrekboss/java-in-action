package org.crayzer.conc.juc.tool.exchange;

import org.crayzer.conc.juc.tool.cyclicBarrier.UseCyclicBarrier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Exchanger;

/**
 * {@link Exchanger} 俗称交换器,用于在线程之间交换数据,但是比较受限,因为只能在两个线程之间交换数据
 *
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
public class UseExchange {

    private static final Logger logger = LoggerFactory.getLogger(UseCyclicBarrier.class);

    private static final Exchanger<Set<String>> EXCHANGER = new Exchanger<>();

    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                Set<String> aSet = new HashSet<>();
                aSet.add("A");
                aSet.add("B");
                aSet.add("C");
                try {
                    Set<String> exchanger = EXCHANGER.exchange(aSet);
                    for (String s : exchanger) {
                        logger.info("aSet" + s);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                Set<String> bSet = new HashSet<>();
                bSet.add("1");
                bSet.add("2");
                bSet.add("3");
                try {
                    Set<String> exchanger = EXCHANGER.exchange(bSet);
                    for (String s : exchanger) {
                        logger.info("bSet" + s);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
