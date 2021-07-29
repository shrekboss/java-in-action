package org.crayzer.design.design_mode_pattern.behavioural.chainOfResponsibility.sample;

public class SexyWordFilter implements SensitiveWordFilter {
    @Override
    public boolean doFilter(Content content) {
        boolean legal = true;
        //...
        return legal;
    }
}

