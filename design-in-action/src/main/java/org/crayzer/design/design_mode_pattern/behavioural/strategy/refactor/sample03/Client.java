package org.crayzer.design.design_mode_pattern.behavioural.strategy.refactor.sample03;

public class Client {
    public static void main(String[] args) {
        int arr[] = {1, 4, 6, 2, 5, 3, 7, 10, 9};
        int result[];
        ArrayHandler ah = new ArrayHandler();

        Sort sort = new QuickSort();
        ah.setSortObj(sort);
        result = ah.sort(arr);

        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + ",");
        }
    }

}
