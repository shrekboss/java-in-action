package org.crayzer.conc.design.pattern.stm.mySTM;

public class SafeAcount {
    private TxnRef<Integer> balance;

    public SafeAcount(int balance) {
        this.balance = new TxnRef<>(balance);
    }

    public void transfer(SafeAcount target, int amt) {
        STM.atomic((txn) -> {
            Integer from = balance.getValue(txn);
            balance.setValue(from - amt, txn);
            Integer to = target.balance.getValue(txn);
            target.balance.setValue(to + amt, txn);
        });
    }
}
