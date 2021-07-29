package org.crayzer.design.design_mode_thingking.inaction.imagestore;


import org.crayzer.design.design_mode_thingking.inaction.imagestore.pojo.Image;

public interface ImageStore {
    String upload(Image image, String buctetName);
    Image download(String url);
}
