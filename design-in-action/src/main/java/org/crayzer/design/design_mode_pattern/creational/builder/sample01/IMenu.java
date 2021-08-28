package org.crayzer.design.design_mode_pattern.creational.builder.sample01;

/**
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 */
public interface IMenu {

    IMenu appendCeiling(Matter matter);
    IMenu appendCoat(Matter matter);
    IMenu appendFloor(Matter matter);
    IMenu appendTile(Matter matter);

    String getDetail();
}
