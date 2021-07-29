package org.crayzer.design.design_mode_thingking.inaction.virtualwallet.controller;

import org.crayzer.design.design_mode_thingking.inaction.virtualwallet.service.VirtualWalletService;

import java.math.BigDecimal;

public class VirtualWalletController {
    // 通过构造函数或者IOC框架注入
    private VirtualWalletService virtualWalletService;

    public BigDecimal getBalance(Long walletId) {
        //...  //查询余额
        return BigDecimal.ZERO;
    }

    public void debit(Long walletId, BigDecimal amount) {
        //出账
    }

    public void credit(Long walletId, BigDecimal amount) {
        //入账
    }

    public void transfer(Long fromWalletId, Long toWalletId, BigDecimal amount) {
        //转账
    }
}
