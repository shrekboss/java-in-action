package org.crayzer.err.coding.springpart1.aopmetrics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    // todo 修改点: 可以自动捕获异常
    @Metrics(ignoreException = true) //启用方法监控
    // @Metrics //启用方法监控
    public void createUser(UserEntity entity) {
        userRepository.save(entity);
        if (entity.getName().contains("test"))
            throw new RuntimeException("invalid username!");
    }

    public int getUserCount(String name) {
        return userRepository.findByName(name).size();
    }
}
