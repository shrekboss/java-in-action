package org.crayzer.err;

/**
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 * @since 1.0.0
 */
public class Student {
    private String id;
    private String name;
    private int age;
    private String city;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Student(String id, String name, int age, String city) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.city = city;
    }

}
