package org.crayzer.design.design_mode_pattern.structural.proxy;

public interface IUserDao {
    void save();
    void edit();
    void delete();

    void saveOrUpdate();
}
