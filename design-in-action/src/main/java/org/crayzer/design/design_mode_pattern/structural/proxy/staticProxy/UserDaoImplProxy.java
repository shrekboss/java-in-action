package org.crayzer.design.design_mode_pattern.structural.proxy.staticProxy;


import org.crayzer.design.design_mode_pattern.structural.proxy.IUserDao;
import org.crayzer.design.design_mode_pattern.structural.proxy.UserDaoImpl;

public class UserDaoImplProxy implements IUserDao {

    private UserDaoImpl target;

    public UserDaoImplProxy(UserDaoImpl target) {
        this.target = target;
    }

    @Override
    public void save() {
        System.out.println("开始保存事务...");
        target.save();
        System.out.println("提交保存事务...");
    }

    @Override
    public void edit() {
        System.out.println("开始编辑事务...");
        target.save();
        System.out.println("提交编辑事务...");
    }

    @Override
    public void delete() {
        System.out.println("开始删除事务...");
        target.save();
        System.out.println("提交删除事务...");
    }

    @Override
    public void saveOrUpdate() {
        System.out.println("开始保存/更新事务...");
        target.save();
        System.out.println("提交保存/更新事务...");
    }
}
