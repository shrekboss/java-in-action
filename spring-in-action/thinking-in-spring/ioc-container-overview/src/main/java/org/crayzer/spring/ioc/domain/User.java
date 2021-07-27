package org.crayzer.spring.ioc.domain;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;

public class User implements BeanNameAware {

    private String name;
    private Long id;
    private City city;
    private City[] workCities;
    private List<City> lifeCities;
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

    public City[] getWorkCities() {
        return workCities;
    }

    public void setWorkCities(City[] workCities) {
        this.workCities = workCities;
    }

    public List<City> getLifeCities() {
        return lifeCities;
    }

    public void setLifeCities(List<City> lifeCities) {
        this.lifeCities = lifeCities;
    }

    public Resource getConfigFileLocation() {
        return configFileLocation;
    }

    public void setConfigFileLocation(Resource configFileLocation) {
        this.configFileLocation = configFileLocation;
    }

    public String getBeanName() {
        return beanName;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", city=" + city +
                ", workcities=" + Arrays.toString(workCities) +
                ", lifecities=" + lifeCities +
                ", configFileLocation=" + configFileLocation +
                ", beanName='" + beanName + '\'' +
                '}';
    }
}
