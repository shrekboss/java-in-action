package org.crayzer.design.design_mode_principle.isp;

import java.util.Map;

/**
 * class_name: Viewer
 * package: org.crayzer.design.design_mode_principle.isp
 * describe: 视图查看接口
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 **/
public interface Viewer {
    String outputInPlainText();
    Map<String, String> output();
}
