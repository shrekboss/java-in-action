package org.crayzer.spring.question;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author crayzer
 */
public class Student {

    private Long id;

    private String name;

    @Autowired
    private ClassRoom classRoom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", classRoom.name=" + classRoom.getName() +
                '}';
    }
}
