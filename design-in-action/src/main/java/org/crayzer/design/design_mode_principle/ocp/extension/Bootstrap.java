package org.crayzer.design.design_mode_principle.ocp.extension;

import org.crayzer.design.design_mode_principle.ocp.extension.vo.ApiStatInfo;

public class Bootstrap {
    public static void main(String[] args) {
        ApiStatInfo apiStatInfo = new ApiStatInfo();
        // ...省略设置apiStatInfo数据值的代码
        apiStatInfo.setErrorCount(100L);
        apiStatInfo.setApi("Test");
        apiStatInfo.setDurationOfSeconds(1000L);
        apiStatInfo.setRequestCount(202L);
        apiStatInfo.setTimeoutCount(2020L);
        ApplicationContext.getInstance().getAlert().check(apiStatInfo);
    }
}
