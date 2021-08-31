package org.crayzer.err.coding.transaction.transactionrollbackfailed;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * 异常无法传播出方法，导致事务无法回滚
     */
    @Transactional
    public void createUserWrong1(String name) {
        try {
            userRepository.save(new UserEntity(name));
            throw new RuntimeException("error");
        } catch (Exception ex) {
            log.error("create user failed", ex);
        }
    }

    /**
     * 即使传播了异常，但是异常是受检查异常也无法让事务回滚
     */
    @Transactional
    public void createUserWrong2(String name) throws IOException {
        userRepository.save(new UserEntity(name));
        otherTask();
    }

    //因为文件不存在，一定会抛出一个IOException
    private void otherTask() throws IOException {
        Files.readAllLines(Paths.get("file-that-not-exist"));
    }

    public int getUserCount(String name) {
        return userRepository.findByName(name).size();
    }

    @Transactional
    public void createUserRight1(String name) {
        try {
            userRepository.save(new UserEntity(name));
            throw new RuntimeException("error");
        } catch (Exception ex) {
            log.error("create user failed", ex);
            // 手动设置让当前事务处于回滚状态：
            // “Transactional code has requested rollback”的提示，表明手动请求回滚：
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        log.info("result {} ", userRepository.findByName(name).size());//为什么这里是1你能想明白吗？
    }

    // 期望遇到所有的 Exception 都回滚事务
    @Transactional(rollbackFor = Exception.class)
    public void createUserRight2(String name) throws IOException {
        userRepository.save(new UserEntity(name));
        otherTask();
    }

}
