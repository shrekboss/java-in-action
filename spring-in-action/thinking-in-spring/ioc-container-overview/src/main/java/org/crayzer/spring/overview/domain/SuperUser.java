package org.crayzer.spring.overview.domain;


import lombok.Data;
import lombok.ToString;
import org.crayzer.spring.overview.annotation.Super;

/**
 * 
 */
@Super
@Data
@ToString
public class SuperUser extends User {

    private String address;
}
