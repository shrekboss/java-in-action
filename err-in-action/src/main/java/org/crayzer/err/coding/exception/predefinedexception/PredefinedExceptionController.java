package org.crayzer.err.coding.exception.predefinedexception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("predefinedexception")
public class PredefinedExceptionController {

    @GetMapping("wrong")
    public void wrong() {
        try {
            createOrderWrong();
        } catch (Exception ex) {
            // 把异常定义为静态变量会导致异常信息固化，这就和异常的栈一定是需要根据当前调用来动态获取相矛盾。
            log.error("createOrder got error", ex);
        }
        try {
            cancelOrderWrong();
        } catch (Exception ex) {
            // 把异常定义为静态变量会导致异常信息固化，这就和异常的栈一定是需要根据当前调用来动态获取相矛盾。
            log.error("cancelOrder got error", ex);
            // - cancelOrder got error
            // ...BusinessException: 订单已经存在
        }
    }

    @GetMapping("right")
    public void right() {

        try {
            createOrderRight();
        } catch (Exception ex) {
            log.error("createOrder got error", ex);
        }
        try {
            cancelOrderRight();
        } catch (Exception ex) {
            log.error("cancelOrder got error", ex);
        }
    }

    private void createOrderWrong() {
        throw Exceptions.ORDEREXISTS;
    }

    private void cancelOrderWrong() {
        throw Exceptions.ORDERCANCEL;
    }

    private void createOrderRight() {
        throw Exceptions.orderExists();
    }

    private void cancelOrderRight() {
        throw Exceptions.orderCancel();
    }
}
