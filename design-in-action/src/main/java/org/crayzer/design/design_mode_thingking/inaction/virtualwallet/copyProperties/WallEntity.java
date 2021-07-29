package org.crayzer.design.design_mode_thingking.inaction.virtualwallet.copyProperties;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WallEntity {
    private Long id;
    private Long createTime;
    private Long updateTime;
    private String name;
    private int number;
    private BigDecimal balance;

    public WallEntity() {
    }

    public WallEntity(long id, String name, int number, BigDecimal balance) {
        this.id = id;
        this.createTime = System.currentTimeMillis();
        this.updateTime = System.currentTimeMillis();
        this.name = name;
        this.number = 0;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "WallEntity{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", balance=" + balance +
                '}';
    }
}
