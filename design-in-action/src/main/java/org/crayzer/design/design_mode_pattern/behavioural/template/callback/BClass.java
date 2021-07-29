package org.crayzer.design.design_mode_pattern.behavioural.template.callback;

public class BClass {
    public void process(ICallback callback) {
        //...
        callback.methodToCallback();
        //...
    }
}
