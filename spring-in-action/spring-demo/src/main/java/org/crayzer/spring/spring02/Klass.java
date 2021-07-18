package org.crayzer.spring.spring02;

import lombok.Data;
import org.crayzer.spring.spring01.Student;

import java.util.List;

@Data
public class Klass { 
    
    List<Student> students;
    
    public void dong(){
        System.out.println(this.getStudents());
    }
    
}
