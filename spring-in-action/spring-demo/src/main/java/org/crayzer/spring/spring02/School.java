package org.crayzer.spring.spring02;

import lombok.Data;
import org.crayzer.spring.aop.ISchool;
import org.crayzer.spring.spring01.Student;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

@Data
public class School implements ISchool {
    
    // Resource 
    @Autowired(required = true) //primary
            org.crayzer.spring.spring02.Klass class1;
    
    @Resource(name = "student100")
    Student student100;
    
    @Override
    public void ding(){
    
        System.out.println("Class1 have " + this.class1.getStudents().size() + " students and one is " + this.student100);
        
    }
    
}
