package org.crayzer.err.coding.nullvalue.pojonull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@Slf4j
@RequestMapping("pojonull")
@RestController
public class POJONullController {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public void test() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        UserDto result = objectMapper.readValue("{\"id\":\"1\", \"age\":30, \"name\":null}", UserDto.class);
        log.info("field name with null value dto:{} name:{}", result, result.getName().orElse("N/A"));
        // field name with null value dto:UserDto(id=1, name=Optional.empty, age=Optional[30]) name:N/A
        log.info("missing field name dto:{}", objectMapper.readValue("{\"id\":\"1\", \"age\":30}", UserDto.class));
        // missing field name dto:UserDto(id=1, name=null, age=Optional[30])
    }

    @PostConstruct
    public void init() {
        User user = userRepository.findById(1L).orElse(null);
        if (user != null) {
            user.setAge(36);
            user.setName("crayzer");
        } else {
            user.setId(1L);
            user.setAge(36);
            user.setName("crayzer");
        }
        userRepository.save(user);

        UserEntity userEntity = userEntityRepository.findById(1L).orElse(null);
        if (userEntity != null) {
            userEntity.setAge(36);
            userEntity.setName("crayzer");
            userEntity.setNickname("guestcrayzer");
        } else {
            user.setId(1L);
            user.setAge(36);
            user.setName("crayzer");
        }
        userEntityRepository.save(userEntity);
    }

    // step1: init.sql
    // step2: curl -H "Content-Type:application/json" -X POST -d '{ "id":1, "name":null}' http://localhost:45678/pojonull/wrong
    // {"id":1,"name":null,"nickname":"guestnull","age":null,"createDate":"2020-01-05T02:01:03.784+0000"}%
    @PostMapping("wrong")
    public User wrong(@RequestBody User user) {
        user.setNickname(String.format("guest%s", user.getName()));
        return userRepository.save(user);
    }


    // init.sql
    // curl -H "Content-Type:application/json" -X POST -d '{ "id":1, "name":null}' http://localhost:45678/pojonull/right
    // {"id":1,"name":"","nickname":"guest","age":36,"createDate":"2020-01-04T11:09:20.000+0000"}%
    @PostMapping("right")
    public UserEntity right(@RequestBody UserDto user) {
        if (user == null || user.getId() == null)
            throw new IllegalArgumentException("用户Id不能为空");

        UserEntity userEntity = userEntityRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("用户不存在"));

        // "name":null uer.getName -> Option.Empty
        if (user.getName() != null) {
            userEntity.setName(user.getName().orElse(""));
        }
        userEntity.setNickname("guest" + userEntity.getName());
        if (user.getAge() != null) {
            userEntity.setAge(user.getAge().orElseThrow(() -> new IllegalArgumentException("年龄不能为空")));
        }
        return userEntityRepository.save(userEntity);
    }
}