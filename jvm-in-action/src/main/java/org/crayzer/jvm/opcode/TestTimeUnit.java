package org.crayzer.jvm.opcode;

import java.util.concurrent.TimeUnit;

public class TestTimeUnit {
    public static void main(String[] args) {
        try {
            TimeUnit.MILLISECONDS.sleep(0L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
