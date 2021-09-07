package org.crayzer.err.coding.springpart1.beanprototye;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public abstract class SayService {
    // 用于保存方法处理的中间数据
    List<String> data = new ArrayList<>();

    // 每次调用 say 方法都会往 data 加入新数据，可以认为 SayService 是有状态，如果 SayService 是单
    // 例的话必然会 OOM：
    public void say() {
        data.add(IntStream.rangeClosed(1, 1000000)
                .mapToObj(__ -> "a")
                .collect(Collectors.joining("")) + UUID.randomUUID().toString());
        log.info("I'm {} size:{}", this, data.size());
    }
}
