package org.crayzer.conc.design.pattern.balking.维护服务的路由关系;

import java.util.Objects;

public final class Router {

    private final String ip;
    private final Integer port;
    private final String iface;
    /* 省略 GetXxx() */

    public Router(String ip, Integer port, String iface) {
        this.ip = ip;
        this.port = port;
        this.iface = iface;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Router router = (Router) o;
        return ip.equals(router.ip) &&
                port.equals(router.port) &&
                iface.equals(router.iface);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port, iface);
    }

    public String getIp() {
        return ip;
    }

    public Integer getPort() {
        return port;
    }

    public String getIface() {
        return iface;
    }
}
