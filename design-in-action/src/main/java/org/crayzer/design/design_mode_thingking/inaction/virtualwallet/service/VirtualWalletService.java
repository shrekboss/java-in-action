package org.crayzer.design.design_mode_thingking.inaction.virtualwallet.service;

import org.crayzer.design.design_mode_thingking.inaction.virtualwallet.Status;
import org.crayzer.design.design_mode_thingking.inaction.virtualwallet.domain.VirtualWallet;
import org.crayzer.design.design_mode_thingking.inaction.virtualwallet.entity.VirtualWalletEntity;
import org.crayzer.design.design_mode_thingking.inaction.virtualwallet.entity.VirtualWalletTransactionEntity;
import org.crayzer.design.design_mode_thingking.inaction.virtualwallet.exception.InsufficientBalanceException;
import org.crayzer.design.design_mode_thingking.inaction.virtualwallet.repository.VirtualWalletRepository;
import org.crayzer.design.design_mode_thingking.inaction.virtualwallet.repository.VirtualWalletTransactionRepository;

import java.math.BigDecimal;

public class VirtualWalletService {
    // 通过构造函数或者IOC框架注入
    private VirtualWalletRepository walletRepo;
    private VirtualWalletTransactionRepository transactionRepo;

    public VirtualWallet getVirtualWallet(Long walletId) {
        VirtualWalletEntity walletEntity = walletRepo.getWalletEntity(walletId);
        VirtualWallet wallet = convert(walletEntity);
        return wallet;
    }

    public BigDecimal getBalance(Long walletId) {
        return walletRepo.getBalance(walletId);
    }

    public void debit(Long walletId, BigDecimal amount) {
        VirtualWalletEntity walletEntity = walletRepo.getWalletEntity(walletId);
        VirtualWallet wallet = convert(walletEntity);
        wallet.debit(amount);
        walletRepo.updateBalance(walletId, wallet.balance());
    }

    public void credit(Long walletId, BigDecimal amount) {
        VirtualWalletEntity walletEntity = walletRepo.getWalletEntity(walletId);
        VirtualWallet wallet = convert(walletEntity);
        wallet.credit(amount);
        walletRepo.updateBalance(walletId, wallet.balance());
    }

    public void transfer(Long fromWalletId, Long toWalletId, BigDecimal amount) {
        VirtualWalletTransactionEntity transactionEntity = new VirtualWalletTransactionEntity();
        transactionEntity.setAmount(amount);
        transactionEntity.setCreateTime(System.currentTimeMillis());
        transactionEntity.setFromWalletId(fromWalletId);
        transactionEntity.setToWalletId(toWalletId);
        transactionEntity.setStatus(Status.TO_BE_EXECUTED);
        Long transactionId = transactionRepo.saveTransaction(transactionEntity);
        try {
            debit(fromWalletId, amount);
            credit(toWalletId, amount);
        } catch (InsufficientBalanceException e) {
            transactionRepo.updateStatus(transactionId, Status.CLOSED);
            //...rethrow exception e...
        } catch (IllegalArgumentException e) {
            transactionRepo.updateStatus(transactionId, Status.FAILED);
        } catch (RuntimeException e1) {
            //...rethrow exception e...
            transactionRepo.updateStatus(transactionId, Status.EXECUTED);
        }
    }

    private VirtualWallet convert(VirtualWalletEntity walletEntity) {
        return null;
    }
}
