package org.crayzer.design.design_mode_pattern.creational.prototype.shallowCopy.sample01;

import java.io.Serializable;

public class Attachment implements Serializable {

    private static final long serialVersionUID = -5704937648799376414L;

    public void downLoad() {
        System.out.println("下载附件！");
    }
}
