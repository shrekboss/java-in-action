package org.crayzer.design.design_mode_pattern.creational.prototype.shallowCopy.sample02;

import org.crayzer.design.design_mode_pattern.creational.prototype.WorkExperience;

import java.util.ArrayList;

public class ResumeShallowCopy implements Cloneable {
    private String name = null;
    private Integer age = null;
    private String sex = null;
    private ArrayList<String> famMem = new ArrayList<>();
    private WorkExperience work = null;

    public ResumeShallowCopy(String name) {
        this.name = name;
        this.work = new WorkExperience();
    }

    public void setName(String name) {
        this.name = name;
    }

    /** 个人信息 */
    public void setPersonal(String sex, int age, ArrayList<String> famMem) {
        this.age = age;
        this.sex = sex;
        this.famMem = famMem;
    }

    /** 工作经历 */
    public void setWork(String timeArea, String company) {
        work.timeArea = timeArea;
        work.company = company;
    }

    public void setFamMem(ArrayList<String> famMem) {
        this.famMem = famMem;
    }

    public ArrayList<String> getFamMem() {
        return famMem;
    }

    @Override
    public ResumeShallowCopy clone() throws CloneNotSupportedException {
        return (ResumeShallowCopy) super.clone();
    }

    public void display() {
        System.out.println(this.name + " " + this.sex + " " + this.age);
        System.out.print("Family member: ");
        for(String elem : famMem)
            System.out.print(elem + " ");
        System.out.println();
        System.out.print("Work experience: " + this.work.timeArea);
        System.out.println(" " + this.work.company);
    }// display
}
