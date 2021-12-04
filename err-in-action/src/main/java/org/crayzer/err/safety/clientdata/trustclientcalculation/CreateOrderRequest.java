package org.crayzer.err.safety.clientdata.trustclientcalculation;

import lombok.Data;

@Data
public class CreateOrderRequest {
    private long itemId;
    private int quantity;
}
