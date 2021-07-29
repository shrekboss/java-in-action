package org.crayzer.design.design_mode_pattern.creational.prototype;

import lombok.Data;

@Data
public class SearchWord {
    private long lastUpdateTime;
    private String keyword;
    private long count;

    public SearchWord(String keyword, long count, long lastUpdateTime) {
        this.keyword = keyword;
        this.count = count;
        this.lastUpdateTime = lastUpdateTime;
    }
}
