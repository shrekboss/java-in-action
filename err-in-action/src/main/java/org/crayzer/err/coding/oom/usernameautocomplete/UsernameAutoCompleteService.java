package org.crayzer.err.coding.oom.usernameautocomplete;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
@Slf4j
public class UsernameAutoCompleteService {


    //自动完成的索引，Key是用户输入的部分用户名，Value是对应的用户数据
    private ConcurrentHashMap<String, List<UserDTO>> autoCompleteIndex = new ConcurrentHashMap<>();

    @Autowired
    private UserRepository userRepository;

//     @PostConstruct
    public void wrong() {
        //先保存10000个用户名随机的用户到数据库中
        userRepository.saveAll(LongStream.rangeClosed(1, 10000).mapToObj(i -> new UserEntity(i, randomName())).collect(Collectors.toList()));

        //从数据库加载所有用户
        userRepository.findAll().forEach(userEntity -> {
            int len = userEntity.getName().length();
            //对于每一个用户，对其用户名的前N位进行索引，N可能是1~6六种长度类型
            for (int i = 0; i < len; i++) {
                String key = userEntity.getName().substring(0, i + 1);
                autoCompleteIndex.computeIfAbsent(key, s -> new ArrayList<>())
                        // 每次都要对象 10000 * 6
                        .add(new UserDTO(userEntity.getName()));
            }
        });
        log.info("autoCompleteIndex size:{} count:{}", autoCompleteIndex.size(),
                autoCompleteIndex.entrySet().stream().map(item -> item.getValue().size()).reduce(0, Integer::sum));
    }

    @PostConstruct
    public void right() {
        userRepository.saveAll(LongStream.rangeClosed(1, 10000).mapToObj(i -> new UserEntity(i, randomName())).collect(Collectors.toList()));

        HashSet<UserDTO> cache = userRepository.findAll().stream()
                .map(item -> new UserDTO(item.getName()))
                .collect(Collectors.toCollection(HashSet::new));

        cache.stream().forEach(userDTO -> {
            int len = userDTO.getName().length();
            for (int i = 0; i < len; i++) {
                String key = userDTO.getName().substring(0, i + 1);
                autoCompleteIndex.computeIfAbsent(key, s -> new ArrayList<>()).add(userDTO);
            }
        });
        log.info("autoCompleteIndex size:{} count:{}", autoCompleteIndex.size(),
                autoCompleteIndex.entrySet().stream().map(item -> item.getValue().size()).reduce(0, Integer::sum));
    }


    /**
     * 随机生成长度为6的英文名称，字母包含 abcdefghij
     */
    private String randomName() {
        return String.valueOf(Character.toChars(ThreadLocalRandom.current().nextInt(10) + 'a')).toUpperCase() +
                String.valueOf(Character.toChars(ThreadLocalRandom.current().nextInt(10) + 'a')) +
                String.valueOf(Character.toChars(ThreadLocalRandom.current().nextInt(10) + 'a')) +
                String.valueOf(Character.toChars(ThreadLocalRandom.current().nextInt(10) + 'a')) +
                String.valueOf(Character.toChars(ThreadLocalRandom.current().nextInt(10) + 'a')) +
                String.valueOf(Character.toChars(ThreadLocalRandom.current().nextInt(10) + 'a'));
    }

    public static void main(String[] args) {
        UsernameAutoCompleteService service = new UsernameAutoCompleteService();
        System.out.println(service.randomName());
    }
}
