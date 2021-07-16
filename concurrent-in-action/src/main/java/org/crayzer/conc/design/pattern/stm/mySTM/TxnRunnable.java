package org.crayzer.conc.design.pattern.stm.mySTM;

@FunctionalInterface
public interface TxnRunnable {
    void run(Txn txn);
}
