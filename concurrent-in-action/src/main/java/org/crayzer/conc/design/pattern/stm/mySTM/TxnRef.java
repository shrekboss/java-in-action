package org.crayzer.conc.design.pattern.stm.mySTM;

public class TxnRef<T> {
    volatile VersionedRef curRef;

    public TxnRef(T value) {
        this.curRef = new VersionedRef(value, 0L);
    }

    //获取当前事务中的数据
    public T getValue(Txn txn) {
        return txn.get(this);
    }

    //在当前事务中设置数据
    public void setValue(T value, Txn txn) {
        txn.set(this, value);
    }
}
