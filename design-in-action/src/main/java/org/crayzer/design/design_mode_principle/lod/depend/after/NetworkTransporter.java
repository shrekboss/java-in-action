package org.crayzer.design.design_mode_principle.lod.depend.after;

/**
 * class_name: NetworkTransporter
 * package: org.crayzer.design.design_mode_principle.lod.depend.before
 * describe: 类负责底层网络通信，根据请求获取数据
 **/
public class NetworkTransporter {
    // 省略属性和其他方法...

    public Byte[] send(String address, Byte[] data) {
        //...
        return null;
    }

    public Byte[] send(Object address, long bytes) {
        return new Byte[0];
    }
}




