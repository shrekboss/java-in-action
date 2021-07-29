package org.crayzer.design.design_mode_refactor.test.refactor;

import org.crayzer.design.design_mode_refactor.test.original.WalletRpcService;

public class MockWalletRpcServiceOne extends WalletRpcService {
    public String moveMoney(Long id, Long fromUserId, Long toUserId, Double amount) {
        return "123bac";
    }
}
