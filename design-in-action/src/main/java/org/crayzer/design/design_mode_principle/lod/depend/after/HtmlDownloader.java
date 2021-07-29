package org.crayzer.design.design_mode_principle.lod.depend.after;

import org.crayzer.design.design_mode_principle.lod.depend.Html;
import org.crayzer.design.design_mode_principle.lod.depend.HtmlRequest;

/**
 * class_name: HtmlDownloader
 * package: org.crayzer.design.design_mode_principle.lod.depend.before
 * describe: 用来通过 URL 获取网页
 **/
public class HtmlDownloader {
    private NetworkTransporter transporter;//通过构造函数或IOC注入

    public Html downloadHtml(String url) {
        HtmlRequest htmlRequest = new HtmlRequest(url);
        Byte[] rawHtml = transporter.send(
                htmlRequest.getAddress(), htmlRequest.getContent().getBytes());
        return new Html(rawHtml);
    }
}
