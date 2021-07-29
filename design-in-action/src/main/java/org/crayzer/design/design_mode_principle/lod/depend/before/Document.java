package org.crayzer.design.design_mode_principle.lod.depend.before;

import org.crayzer.design.design_mode_principle.lod.depend.Html;

/**
 * class_name: Document
 * package: org.crayzer.design.design_mode_principle.lod.depend.before
 * describe: 表示网页文档，后续的网页内容抽取、分词、索引都是以此为处理对象
 **/
public class Document {
    private Html html;
    private String url;

    /**
     * 问题比较多，主要要有三点:
     * 第一，构造函数中的 downloader.downloadHtml() 逻辑复杂，耗时长，不应该放到构造函数中，会影响代码的可测试性。
     * 代码的可测试性我们后面会讲到，这里你先知道有这回事就可以了。
     *
     * 第二，HtmlDownloader 对象在构造函数中通过 new 来创建，违反了基于接口而非实现编程的设计思想，
     * 也会影响到代码的可测试性。
     *
     * 第三，从业务含义上来讲，Document 网页文档没必要依赖 HtmlDownloader 类，违背了迪米特法则
     **/
    public Document(String url) {
        this.url = url;
        HtmlDownloader downloader = new HtmlDownloader();
        this.html = downloader.downloadHtml(url);
    }
    //...
}
