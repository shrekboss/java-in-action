package org.crayzer.err.coding.serialization.jsonignoreproperties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("jsonignoreproperties")
@Slf4j
public class JsonIgnorePropertiesController {

    @Autowired
    private ObjectMapper objectMapper;

    // 重新定义了一个 ObjectMapper Bean，开启了 WRITE_ENUMS_USING_INDEX 功能特性
    @GetMapping("test")
    public void test() throws JsonProcessingException {
        log.info("color:{}", objectMapper.writeValueAsString(Color.BLUE));
    }

    // {
    //   "name": "demoData",
    //   "version":1
    // }
    @PostMapping("wrong")
    public UserWrong wrong(@RequestBody UserWrong user) {
        return user;
    }

    @PostMapping("right")
    public Object right(@RequestBody UserRight user) {
        return user;
    }

    enum Color {
        RED, BLUE
    }
}
