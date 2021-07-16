package org.crayzer.conc.design.pattern.balking;

/**
 * Balking 模式有一个非常典型的应用场景就是单次初始化
 */
public class InitTest {
    private boolean inited = false;

    synchronized void init() {
        if (inited) {
            return;
        }
        doInit();
        inited = true;
    }

    private void doInit() {
        // TODO
    }
}
