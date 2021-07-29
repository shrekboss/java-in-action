package org.crayzer.design.design_mode_pattern.creational.singleton.register.enumSingleton;

public enum EnumSingleton {
    RED(){
        private int r = 255;
        private int g = 0;
        private int b = 0;

    },BLACK(){
        private int r = 0;
        private int g = 0;
        private int b = 0;
    },WHITE(){
        private int r = 255;
        private int g = 255;
        private int b = 255;
    };

    public void func1() {

    }

    public static void main(String[] args) {
        System.out.println(EnumSingleton.RED);
    }
}
