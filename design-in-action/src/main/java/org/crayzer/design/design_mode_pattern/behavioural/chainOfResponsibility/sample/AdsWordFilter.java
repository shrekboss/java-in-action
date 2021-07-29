package org.crayzer.design.design_mode_pattern.behavioural.chainOfResponsibility.sample;

public class AdsWordFilter implements SensitiveWordFilter {
    @Override
    public boolean doFilter(Content content) {
        boolean legal = true;
        //...
        return legal;
    }
}
