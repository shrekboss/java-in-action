package org.crayzer.design.design_mode_refactor.test.original;


import lombok.Getter;
import org.crayzer.design.design_mode_refactor.test.IdGenerator;
import org.crayzer.design.design_mode_refactor.test.RedisDistributedLock;
import org.crayzer.design.design_mode_refactor.test.STATUS;

import javax.transaction.InvalidTransactionException;

@Getter
public class Transaction {
    private String id;
    private Long buyerId;
    private Long sellerId;
    private Long productId;
    private Long orderId;
    private Long createTimestamp;
    private Double amount;
    private STATUS status;
    private String walletTransactionId;

    public Transaction(String preAssignedId, Long buyerId, Long sellerId, Long productId,  Long orderId) {
        if (preAssignedId != null && !preAssignedId.isEmpty()) {
            this.id = preAssignedId;
        } else {
            this.id = IdGenerator.generateTransactionId();
        }
        if (!this.id.startsWith("t_")) {
            this.id = "t_" + preAssignedId;
        }
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.productId = productId;
        this.orderId = orderId;
        this.status = STATUS.TO_BE_EXECUTD;
        this.createTimestamp = System.currentTimeMillis();
    }

    public boolean execute() throws InvalidTransactionException {
        if ((buyerId == null || (sellerId == null || amount < 0.0))) {
            throw new InvalidTransactionException(/*...*/);
        }

        if (status == STATUS.EXECUTED) return true;
        boolean isLocked = false;
        try {
            // 修改点1
            isLocked = RedisDistributedLock.getSingletonInstance().lockTransaction(id);
            if (!isLocked) {
                return false; // 锁定未成功，返回false，job兜底执行
            }
            if (status == STATUS.EXECUTED) return true; // double check
            long executionInvokedTimestamp = System.currentTimeMillis();
            if (executionInvokedTimestamp - createTimestamp > 14) {
                this.status = STATUS.EXPIRED;
                return false;
            }
            // 修改点2
            WalletRpcService walletRpcService = new WalletRpcService();
            String walletTransactionId = walletRpcService.moveMoney(id, buyerId, sellerId, amount);
            if (walletTransactionId != null) {
                this.walletTransactionId = walletTransactionId;
                this.status = STATUS.EXECUTED;
                return true;
            } else {
                this.status = STATUS.FAILED;
                return false;
            }
        } finally {
            if (isLocked) {
                // 修改点3
                RedisDistributedLock.getSingletonInstance().unlockTransaction(id);
            }
        }
    }

    public void testExecute() throws InvalidTransactionException {
        Long buyerId = 123L;
        Long sellerId = 234L;
        Long productId = 345L;
        Long orderId = 456L;
        Transaction transaction = new Transaction(null, buyerId, sellerId, productId, orderId);
        boolean executedResult = transaction.execute();
        //assertTrue(executedResult);
    }
}
