package org.crayzer.spring.di.annotation;

import java.lang.annotation.*;

/**
 * 自定义依赖注入注解
 *
 * @author Crayzer
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InjectedUser {
}
