package org.crayzer.conc.design.pattern.balking.autosaveeditor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SafeAutoSaveEditor {

    boolean changed = false;
    ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

    public void startAutoSave() {
        ses.scheduleWithFixedDelay(() -> { autoSave(); }, 5, 5, TimeUnit.SECONDS);
    }

    void autoSave(){
        synchronized(this){
            if (!changed) {
                return;
            }
            changed = false;
        }
        //执行存盘操作
        //省略且实现
        this.execSave();
    }

    void edit(){
        //省略编辑逻辑
        synchronized(this){
            changed = true;
        }
    }

    private void execSave() {/*...*/}
}
