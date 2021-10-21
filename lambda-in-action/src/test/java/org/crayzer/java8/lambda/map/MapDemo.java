package org.crayzer.java8.lambda.map;

import org.junit.Test;

import java.util.Optional;

/**
 * map VS flatMap
 * <p>这两个函数，在函数体上没什么区别。唯一区别的就是入参，
 * map函数所接受的入参类型为Function <? super T, ? extends U>，
 * 而flapMap的入参类型为Function<? super T, Optional<U>>
 *
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 * @since 1.0.0
 */
public class MapDemo {

    @Test
    public void test() {
        UserForMap user = new UserForMap();
        String name = Optional.ofNullable(user).map(u -> u.getName()).get();
        System.out.println(name);
    }

    @Test
    public void test1() {
        UserForFlatMap user = new UserForFlatMap();
        String name = Optional.ofNullable(user).flatMap(u -> u.getName()).get();

        System.out.println(name);
    }
}

class UserForFlatMap {
    private String name;

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }
}

class UserForMap {
    private String name;

    public String getName() {
        return name;
    }
}
