package org.crayzer.design.design_mode_principle.ocp.extension;


import org.crayzer.design.design_mode_principle.ocp.extension.handler.AlertHandler;
import org.crayzer.design.design_mode_principle.ocp.extension.vo.ApiStatInfo;

import java.util.ArrayList;
import java.util.List;

public class Alert {
    public Alert() {

    }
    private List<AlertHandler> alertHandlers = new ArrayList<>();

    public void addAlertHandler(AlertHandler alertHandler) {
        this.alertHandlers.add(alertHandler);
    }

    public void check(ApiStatInfo apiStatInfo) {
        for (AlertHandler handler : alertHandlers) {
            handler.check(apiStatInfo);
        }
    }
}

