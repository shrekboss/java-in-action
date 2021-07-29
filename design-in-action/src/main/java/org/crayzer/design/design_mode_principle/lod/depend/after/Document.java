package org.crayzer.design.design_mode_principle.lod.depend.after;

import org.crayzer.design.design_mode_principle.lod.depend.Html;

/**
 * class_name: Document
 * package: org.crayzer.design.design_mode_principle.lod.depend.before
 * describe: 表示网页文档，后续的网页内容抽取、分词、索引都是以此为处理对象
 **/
public class Document {
    private Html html;
    private String url;

    public Document(String url, Html html) {
        this.html = html;
        this.url = url;
    }
    //...
}
