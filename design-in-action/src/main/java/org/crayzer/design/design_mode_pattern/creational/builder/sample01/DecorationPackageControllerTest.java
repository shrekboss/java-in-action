package org.crayzer.design.design_mode_pattern.creational.builder.sample01;

/**
 * @author yizhe.chen
 */
public class DecorationPackageControllerTest {

    public static void main(String[] args) {
        Builder builder = new Builder();
        // 豪华欧式
        System.out.println(builder.levelOne(132.52D).getDetail());
        // 轻奢⽥园
        System.out.println(builder.levelTwo(98.25D).getDetail());
        // 现代简约
        System.out.println(builder.levelThree(85.43D).getDetail());
    }
}
