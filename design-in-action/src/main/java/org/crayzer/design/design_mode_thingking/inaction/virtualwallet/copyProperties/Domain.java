package org.crayzer.design.design_mode_thingking.inaction.virtualwallet.copyProperties;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Domain {
    private Long id;
    private Long createTime;
    private Long updateTime;
    private String name;
    private int number;
    private BigDecimal balance;

    public Domain(String name, int number, BigDecimal balance) {
        this.createTime = System.currentTimeMillis();
        this.updateTime = System.currentTimeMillis();
        this.name = name;
        this.number = number;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Domain{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", balance=" + balance +
                '}';
    }
}
