package org.crayzer.design.design_mode_principle.lod.depend.before;

import org.crayzer.design.design_mode_principle.lod.depend.HtmlRequest;

/**
 * class_name: NetworkTransporter
 * package: org.crayzer.design.design_mode_principle.lod.depend.before
 * describe: 类负责底层网络通信，根据请求获取数据
 **/
public class NetworkTransporter {
    // 省略属性和其他方法...

    /**
     * 作为一个底层网络通信类，我们希望它的功能尽可能通用，而不只是服务于下载 HTML，
     * 所以，我们不应该直接依赖太具体的发送对象 HtmlRequest
     */
    public Byte[] send(HtmlRequest htmlRequest) {
        //...
        return null;
    }
}




