package org.crayzer.jvm.opcode;

import java.io.Serializable;

public class TestSerializable implements Serializable {
    public static final long serialVersionUID = 1;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static void main(String[] args) {

    }
}
