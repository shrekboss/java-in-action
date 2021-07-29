package org.crayzer.design.design_mode_pattern.behavioural.strategy.refactor.sample03;

public class ArrayHandler {
    private Sort sortObj;

    public void setSortObj(Sort sortObj) {
        this.sortObj = sortObj;
    }

    public int[] sort(int arr[]) {
        sortObj.sort(arr);
        return arr;
    }
}
