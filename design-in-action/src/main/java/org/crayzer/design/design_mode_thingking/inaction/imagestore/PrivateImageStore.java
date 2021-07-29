package org.crayzer.design.design_mode_thingking.inaction.imagestore;

import org.crayzer.design.design_mode_thingking.inaction.imagestore.pojo.Image;

public class PrivateImageStore implements ImageStore {

    //...省略属性、构造函数等...

    @Override
    public String upload(Image image, String bucketName) {
        createBucketIfNotExisting(bucketName);
        //...上传图片到私有云...
        //...返回图片的url...
        return "";
    }

    @Override
    public Image download(String url) {
        //...从私有云下载图片...
        return new Image();
    }

    private void createBucketIfNotExisting(String bucketName) {
        // ...创建bucket...
        //...失败会抛出异常..
    }
}
