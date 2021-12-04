package org.crayzer.err.safety.clientdata.trustclientcalculation;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Order {
    private long itemId; //商品ID
    private BigDecimal itemPrice; //商品价格
    private int quantity; //商品数量
    private BigDecimal itemTotalPrice; //商品总价
}
