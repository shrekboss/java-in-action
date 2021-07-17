package org.crayzer.jvm.opcode;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.*;

public class TestSpringTransaction {
    @Transactional
    public Long doSave(Integer taskType) {
        // 执行某些事物操作XXXXX
        Long taskId = 0L;
        notifyPushTaskEngine(taskId);
        return taskId;
    }
    private void notifyPushTaskEngine(final Long taskId) {
        // 通知推送引擎; 事物完成后执行
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronizationAdapter() {
                    @Override
                    public void afterCommit() {
                        // doSomething
                    }
                });
    }
}
