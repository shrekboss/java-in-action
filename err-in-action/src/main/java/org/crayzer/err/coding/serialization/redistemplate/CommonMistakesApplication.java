package org.crayzer.err.coding.serialization.redistemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonMistakesApplication.class, args);
    }

    // 127.0.0.1:6379> keys *Template
    // 1) "stringRedisTemplate"
    // 2) "\xac\xed\x00\x05t\x00\rredisTemplate"
    // todo /redistemplate/wrong && /redistemplate/wrong
    // @Bean
    // public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    //     RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
    //     redisTemplate.setConnectionFactory(redisConnectionFactory);
    //     redisTemplate.afterPropertiesSet();
    //     return redisTemplate;
    // }

    // 自定义 RedisTemplate 的 Key 和 Value 的序列化方式
    // 127.0.0.1:6379> keys *Template
    // 1) "stringRedisTemplate"
    // 2) "redisTemplate"
    // todo /redistemplate/right2
    // @Bean
    // public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    //     RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
    //     redisTemplate.setConnectionFactory(redisConnectionFactory);
    //     Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
    //     redisTemplate.setKeySerializer(RedisSerializer.string());
    //     redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
    //     redisTemplate.setHashKeySerializer(RedisSerializer.string());
    //     redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
    //     redisTemplate.afterPropertiesSet();
    //     return redisTemplate;
    // }

    // 自定义 RedisTemplate 的 Key 和 Value 的序列化方式
    // 127.0.0.1:6379> keys *Template
    // 1) "stringRedisTemplate"
    // 2) "redisTemplate"
    // 修复方式是，修改自定义 RestTemplate 的代码，把 new 出来的 Jackson2JsonRedisSerializer 设置
    // 一个自定义的 ObjectMapper，启用 activateDefaultTyping 方法把类型信息作为属性写入序列化后的数据中
    // todo /redistemplate/right2 | /redistemplate/right3
    // @Bean
    // public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    //     RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
    //     redisTemplate.setConnectionFactory(redisConnectionFactory);
    //
    //     // +++ start
    //     Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     // 把类型信息作为属性写入Value
    //     objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
    //     jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
    //     // +++ end
    //
    //     redisTemplate.setKeySerializer(RedisSerializer.string());
    //     redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
    //     redisTemplate.setHashKeySerializer(RedisSerializer.string());
    //     redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
    //     redisTemplate.afterPropertiesSet();
    //     return redisTemplate;
    // }

    // 或者，直接使用 RedisSerializer.json() 快捷方法，它内部使用的 GenericJackson2JsonRedisSerializer
    // 直接设置了把类型作为属性保存到 Value 中
    // todo /redistemplate/right2 | /redistemplate/right3
    @Bean
    public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(RedisSerializer.json());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    // @Bean
    // public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    //     RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
    //     redisTemplate.setConnectionFactory(redisConnectionFactory);
    //     Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     objectMapper.enable(DeserializationFeature.USE_LONG_FOR_INTS);
    //     //把类型信息作为属性写入Value
    //     objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
    //     jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
    //     redisTemplate.setKeySerializer(RedisSerializer.string());
    //     redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
    //     redisTemplate.setHashKeySerializer(RedisSerializer.string());
    //     redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
    //     redisTemplate.afterPropertiesSet();
    //     return redisTemplate;
    // }

}

