package org.crayzer.conc.design.pattern.stm;

public class UnsafeAccount {
    private long balance;

    public UnsafeAccount(long balance) {
        this.balance = balance;
    }

    public void transfer(UnsafeAccount target, long amt) {
        if (this.balance > amt) {
            this.balance -= amt;
            target.balance = balance;
        }
    }
}
