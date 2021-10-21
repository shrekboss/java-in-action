package org.crayzer.java8.lambda.orelse;

import org.crayzer.java8.User;
import org.junit.Test;

import java.util.Optional;

/**
 * orElse VS orElseGet
 *
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 * @since 1.0.0
 */
public class OrElseDemo {

    /**
     * <p>这两个函数的区别：当user值不为null时，orElse函数依然会执行createUser()方法，而orElseGet函数并不会执行createUser()方法，大家可自行测试。
     */
    @Test
    public void test() {
        User user = null;
        user = Optional.ofNullable(user).orElse(createUser());
        System.out.println(user);
        user = null;
        user = Optional.ofNullable(user).orElseGet(() -> createUser());
        System.out.println(user);
    }

    /**
     * <p>orElseThrow，就是value值为null时,直接抛一个异常出去
     *
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        User user = null;
        Optional.ofNullable(user).orElseThrow(() -> new Exception("用户不存在"));
    }

    public User createUser() {
        User user = new User();
        user.setEmail("yeqi@gmail.com");
        return user;
    }


    // 以前写法
//    public User getUser(User user) throws Exception {
//        if (user != null) {
//            String name = user.getName();
//            if ("zhangsan".equals(name)) {
//                return user;
//            }
//        } else {
//            user = new User();
//            user.setName("zhangsan");
//            return user;
//        }
//        return null;
//    }

    // Java8写法
    public User getUser(User user) {
        return Optional.ofNullable(user)
                .filter(u -> "zhangsan".equals(u.getName()))
                .orElseGet(() -> {
                    User user1 = new User();
                    user1.setName("zhangsan");
                    return user1;
                });
    }
}
