package org.crayzer.err.design.productionready.health;

import org.springframework.boot.actuate.health.CompositeHealthContributor;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.NamedContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
// CompositeHealthContributor 来聚合多个 HealthContributor，实现一组线程池的监控
public class ThreadPoolsHealthContributor implements CompositeHealthContributor {

    //保存所有的子HealthContributor
    private Map<String, HealthContributor> contributors = new HashMap<>();

    ThreadPoolsHealthContributor() {
        //对应ThreadPoolProvider中定义的两个线程池
        this.contributors.put("demoThreadPool", new ThreadPoolHealthIndicator(ThreadPoolProvider.getDemoThreadPool()));
        this.contributors.put("ioThreadPool", new ThreadPoolHealthIndicator(ThreadPoolProvider.getIOThreadPool()));
    }

    @Override
    public HealthContributor getContributor(String name) {
        //根据name找到某一个HealthContributor
        return contributors.get(name);
    }

    @Override
    public Iterator<NamedContributor<HealthContributor>> iterator() {
        //返回NamedContributor的迭代器，NamedContributor也就是Contributor实例+一个命名
        return contributors.entrySet().stream()
                .map((entry) -> NamedContributor.of(entry.getKey(), entry.getValue())).iterator();
    }
}
