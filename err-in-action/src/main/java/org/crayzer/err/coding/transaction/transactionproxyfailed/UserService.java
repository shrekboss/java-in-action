package org.crayzer.err.coding.transaction.transactionproxyfailed;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService self;

    @PostConstruct
    public void init() {
        log.info("this {} self {}", this.getClass().getName(), self.getClass().getName());
    }

    /**
     * 私有方法
     * @Transactional 生效原则 1，除非特殊配置（比如使用 AspectJ 静态织入实现 AOP），否则只有定义
     * 在 public 方法上的 @Transactional 才能生效
     * <p>
     * Spring 默认通过动态代理的方式实现 AOP，对目标方法进行增强，private 方法无法代理到，Spring
     * 自然也无法动态增强事务处理逻辑。
     */
    public int createUserWrong1(String name) {
        try {
            this.createUserPrivate(new UserEntity(name));
        } catch (Exception ex) {
            log.error("create user failed because {}", ex.getMessage());
        }
        return userRepository.findByName(name).size();
    }

    /**
     * 自调用
     * @Transactional 生效原则 2，必须通过代理过的类从外部调用目标方法才能生效。
     * spring 通过 AOP 技术对方法进行增s强，要调用增强过的方法必然是调用代理后的对象。
     * @param name
     * @return
     */
    public int createUserWrong2(String name) {
        try {
            this.createUserPublic(new UserEntity(name));
            // self.createUserPublic(new UserEntity(name)); 正确
        } catch (Exception ex) {
            log.error("create user failed because {}", ex.getMessage());
        }
        return userRepository.findByName(name).size();
    }

    //重新注入自己
    public int createUserRight(String name) {
        try {
            self.createUserPublic(new UserEntity(name));
        } catch (Exception ex) {
            log.error("create user failed because {}", ex.getMessage());
        }
        return userRepository.findByName(name).size();
    }

    //不出异常
    @Transactional
    public int createUserWrong3(String name) {
        try {
            this.createUserPublic(new UserEntity(name));
        } catch (Exception ex) {
            log.error("create user failed because {}", ex.getMessage());
        }
        return userRepository.findByName(name).size();
    }

    @Transactional
    // 针对 private 方法启用事务，动态代理方式的 AOP 不可行，
    // 需要使用静态织入方式的 AOP，也就是在编译期间织入事务增强代码
    private void createUserPrivate(UserEntity entity) {
        userRepository.save(entity);
        if (entity.getName().contains("test"))
            throw new RuntimeException("invalid username!");
    }

    //可以传播出异常
    @Transactional
    public void createUserPublic(UserEntity entity) {
        userRepository.save(entity);
        if (entity.getName().contains("test"))
            throw new RuntimeException("invalid username!");
    }

    public int getUserCount(String name) {
        return userRepository.findByName(name).size();
    }
}
