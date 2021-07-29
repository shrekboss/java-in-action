package org.crayzer.design.design_mode_pattern.creational.prototype.deepClone.sample02;

import java.util.ArrayList;

public class ResumeDeepCopyTest implements Cloneable {
    public static void main(String[] args) throws CloneNotSupportedException {
        ArrayList<String> famMem = new ArrayList<>(); // 家庭成员名单  
        famMem.add("Papa");
        famMem.add("Mama");

        // 创建初始简历  
        ResumeDeepCopy resume1 = new ResumeDeepCopy("Jobs");
        resume1.setPersonal("Male", 26, famMem);
        resume1.setWork("2013/8/1 - 2015/6/30", "Huawei");

        // 通过简历1复制出简历2，并对家庭成员和工作经验进行修改  
        ResumeDeepCopy resume2 = resume1.clone();
        resume2.setName("Tom");
        resume2.getFamMem().add("brother");
        resume2.setWork("2015/7/1 - 2016/6/30", "Baidu");

        resume1.display();
        resume2.display();
    }
}
