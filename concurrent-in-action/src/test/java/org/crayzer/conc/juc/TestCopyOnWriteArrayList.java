package org.crayzer.conc.juc;

import org.junit.Test;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * {@link CopyOnWriteArrayList} 写入并复制
 *
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 * @since 1.0.0
 */
public class TestCopyOnWriteArrayList {

    @Test
    public void copyOnWriteArrayListTest() {
        HelloThread ht = new HelloThread();
        for (int i = 0; i < 10; i++) {
            new Thread(ht).start();
        }
    }
}

class HelloThread implements Runnable {
    //    private static List<String> list = Collections.synchronizedList(new ArrayList<String>());

    // 每次修改都会复制，添加操作多时不适合选这个
    private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

    static {
        list.add("AA");
        list.add("BB");
        list.add("CC");
    }

    @Override
    public void run() {
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
            list.add("AA");//边迭代边添加 会出现并发修改异常
        }
    }
}

