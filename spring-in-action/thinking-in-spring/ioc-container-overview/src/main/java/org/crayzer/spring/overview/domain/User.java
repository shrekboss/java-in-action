package org.crayzer.spring.overview.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;

@Data
@ToString
public class User implements BeanNameAware {

    private String name;
    private Long id;
    private City city;
    private City[] workcities;
    private List<City> lifecities;
    private Resource configFileLocation;

    /**
     * 当前 Bean 的名称
     */
    private transient String beanName;

    public static User createUser() {
        User user = new User();
        user.setId(1L);
        user.setName("crayzer");
        return user;
    }

    @PostConstruct
    public void init() {
        System.out.println("User Bean [" + beanName + "] @PostConstruct 初始化...");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("User Bean [" + beanName + "] @PreDestroy 销毁中...");
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }
}
