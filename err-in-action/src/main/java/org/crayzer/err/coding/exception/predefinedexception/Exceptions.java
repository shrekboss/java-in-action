package org.crayzer.err.coding.exception.predefinedexception;

public class Exceptions {

    public static BusinessException ORDEREXISTS = new BusinessException("订单已经存在", 3001);
    public static BusinessException ORDERCANCEL = new BusinessException("订单取消失败", 3002);

    public static BusinessException orderExists() {
        return new BusinessException("订单已经存在", 3001);
    }

    public static BusinessException orderCancel() {
        return new BusinessException("订单取消失败", 3002);
    }
}
