package org.crayzer.err.design.redundantcode.templatemethod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CommonMistakesApplication {

    // 购物车下单的功能，针对不同用户进行不同处理：
    // 普通用户需要收取运费，运费是商品价格的 10%，无商品折扣；
    // VIP 用户同样需要收取商品价格 10% 的快递费，但购买两件以上相同商品时，第三件开始享受一定折扣；
    // 内部用户可以免运费，无商品折扣。
    public static void main(String[] args) {
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}

