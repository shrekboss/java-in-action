package org.crayzer.spring.overview.domain;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;

/**
 * 
 */
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public City[] getWorkcities() {
        return workcities;
    }

    public void setWorkcities(City[] workcities) {
        this.workcities = workcities;
    }

    public List<City> getLifecities() {
        return lifecities;
    }

    public void setLifecities(List<City> lifecities) {
        this.lifecities = lifecities;
    }

    public Resource getConfigFileLocation() {
        return configFileLocation;
    }

    public void setConfigFileLocation(Resource configFileLocation) {
        this.configFileLocation = configFileLocation;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", city=" + city +
                ", workcities=" + Arrays.toString(workcities) +
                ", lifecities=" + lifecities +
                ", configFileLocation=" + configFileLocation +
                ", beanName='" + beanName + '\'' +
                '}';
    }

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
