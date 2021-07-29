package org.crayzer.design.design_mode_thingking.inaction.virtualwallet.copyProperties;

import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

public class BootStrap {
    public static void main(String[] args) {
        Domain domain = new Domain("DomainToWallEntity", 0, BigDecimal.ZERO);
        System.out.println(domain);
        WallEntity entity = new WallEntity();
        BeanUtils.copyProperties(domain, entity);
        System.out.println(entity);
    }
}
