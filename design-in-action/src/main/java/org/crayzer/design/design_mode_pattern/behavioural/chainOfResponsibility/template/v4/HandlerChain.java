package org.crayzer.design.design_mode_pattern.behavioural.chainOfResponsibility.template.v4;

import java.util.ArrayList;
import java.util.List;

public class HandlerChain {
    private List<IHandler> handlers = new ArrayList<>();

    public void addHandler(IHandler handler) {
        this.handlers.add(handler);
    }

    public void handle() {
        for (IHandler handler : handlers) {
            handler.handle();
        }
    }
}
