package org.crayzer.design.design_mode_refactor.test.refactor;


import lombok.Getter;
import org.crayzer.design.design_mode_refactor.test.IdGenerator;
import org.crayzer.design.design_mode_refactor.test.STATUS;
import org.crayzer.design.design_mode_refactor.test.original.WalletRpcService;

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

    private TransactionLock lock;
    // 添加一个成员变量及其set方法
    private WalletRpcService walletRpcService;

    public void setWalletRpcService(WalletRpcService walletRpcService) {
        this.walletRpcService = walletRpcService;
    }

    public void setTransactionLock(TransactionLock lock) {
        this.lock = lock;
    }

    // 修改点4
    protected boolean isExpired() {
        long executionInvokedTimestamp = System.currentTimeMillis();
        return executionInvokedTimestamp - createTimestamp > 14;
    }

    public Transaction(String preAssignedId, Long buyerId, Long sellerId, Long productId, Long orderId) {
        // 修改点 5
        fillTransactionId(preAssignedId);
        // if (preAssignedId != null && !preAssignedId.isEmpty()) {
        //     this.id = preAssignedId;
        // } else {
        //     this.id = IdGenerator.generateTransactionId();
        // }
        // if (!this.id.startsWith("t_")) {
        //     this.id = "t_" + preAssignedId;
        // }
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.productId = productId;
        this.orderId = orderId;
        this.status = STATUS.TO_BE_EXECUTD;
        this.createTimestamp = System.currentTimeMillis();
    }

    protected void fillTransactionId(String preAssignedId) {
        if (preAssignedId != null && !preAssignedId.isEmpty()) {
            this.id = preAssignedId;
        } else {
            this.id = IdGenerator.generateTransactionId();
        }
        if (!this.id.startsWith("t_")) {
            this.id = "t_" + preAssignedId;
        }
    }

    public boolean execute() throws InvalidTransactionException {
        if ((buyerId == null || (sellerId == null || amount < 0.0))) {
            throw new InvalidTransactionException(/*...*/);
        }

        if (isExpired()) {
            this.status = STATUS.EXPIRED;
            return false;
        }

        if (status == STATUS.EXECUTED) return true;
        boolean isLocked = false;
        try {
            // 修改点1
            // isLocked = RedisDistributedLock.getSingletonInstance().lockTransaction(id);
            isLocked = lock.lock(id);

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
            // WalletRpcService walletRpcService = new WalletRpcService();
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
                // RedisDistributedLock.getSingletonInstance().unlockTransaction(id);
                if (isLocked) {
                    lock.unlock(id);
                }
            }
        }
    }

    public void testExecute() throws InvalidTransactionException {
        Long buyerId = 123L;
        Long sellerId = 234L;
        Long productId = 345L;
        Long orderId = 456L;

        TransactionLock mockLock = new TransactionLock() {
            public boolean lock(String id) {
                return true;
            }

            public void unlock(String id) {
            }
        };

        Transaction transaction = new Transaction(null, buyerId, sellerId, productId, orderId);
        // 使用mock对象来替代真正的RPC服务
        transaction.setWalletRpcService(new MockWalletRpcServiceOne());
        transaction.setTransactionLock(mockLock);
        boolean executedResult = transaction.execute();
        //assertTrue(executedResult);
    }


    public void testExecute_with_TransactionIsExpired() throws InvalidTransactionException {
        Long buyerId = 123L;
        Long sellerId = 234L;
        Long productId = 345L;
        Long orderId = 456L;
        Transaction transaction = new Transaction(null, buyerId, sellerId, productId, orderId) {
            protected boolean isExpired() {
                return true;
            }
        };
        boolean actualResult = transaction.execute();
        // assertFalse(actualResult);
        // assertEquals(STATUS.EXPIRED, transaction.getStatus());
    }
}
