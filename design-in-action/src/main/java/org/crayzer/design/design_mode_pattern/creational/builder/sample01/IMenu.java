package org.crayzer.design.design_mode_pattern.creational.builder.sample01;

/**
 * @author yizhe.chen
 */
public interface IMenu {

    IMenu appendCeiling(Matter matter);
    IMenu appendCoat(Matter matter);
    IMenu appendFloor(Matter matter);
    IMenu appendTile(Matter matter);

    String getDetail();
}
