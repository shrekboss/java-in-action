package org.crayzer.err.coding.serialization.deserializationconstructor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("deserializationconstructor")
@Slf4j
public class DeserializationConstructorController {
    @Autowired
    ObjectMapper objectMapper;

    /**
     * 默认情况下，在反序列化的时候，Jackson 框架只会调用无参构造方法创建对象。如果走自定义的构造方法创
     * 建对象，需要通过 @JsonCreator 来指定构造方法，并通过 @JsonProperty 设置构造方法中参数对应的
     * JSON 属性名
     */
    @GetMapping("wrong")
    public void wrong() throws JsonProcessingException {
        log.info("result :{}", objectMapper.readValue("{\"code\":1234}", APIResultWrong.class));
        log.info("result :{}", objectMapper.readValue("{\"code\":2000}", APIResultWrong.class));
    }

    @GetMapping("right")
    public void right() throws JsonProcessingException {
        log.info("result :{}", objectMapper.readValue("{\"code\":1234}", APIResultRight.class));
        log.info("result :{}", objectMapper.readValue("{\"code\":2000}", APIResultRight.class));
    }
}
