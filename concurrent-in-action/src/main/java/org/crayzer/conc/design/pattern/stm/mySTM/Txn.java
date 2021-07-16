package org.crayzer.conc.design.pattern.stm.mySTM;

public interface Txn {
    <T> T get(TxnRef<T> ref);

    <T> void set(TxnRef<T> ref, T value);
}
