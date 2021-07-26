package org.crayzer.spring.ioc.domain;


import lombok.Data;
import lombok.ToString;
import org.crayzer.spring.ioc.annotation.Super;

/**
 * 
 */
@Super
@Data
@ToString
public class SuperUser extends User {

    private String address;
}
