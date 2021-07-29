package org.crayzer.design.design_mode_pattern.structural.proxy;

public class UserDaoImpl implements IUserDao {
    @Override
    public void save() {
        System.out.println("保存用户数据！");
    }

    @Override
    public void edit() {
        System.out.println("编辑用户数据！");
    }

    @Override
    public void delete() {
        System.out.println("删除用户数据！");
    }

    @Override
    public void saveOrUpdate() {
        System.out.println("修改/添加用户数据！");
    }
}
