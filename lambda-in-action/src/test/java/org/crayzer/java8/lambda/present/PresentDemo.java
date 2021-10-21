package org.crayzer.java8.lambda.present;

import org.crayzer.java8.User;

import java.util.Optional;

/**
 * isPreset VS ifPresent && filter
 * <p>isPresent 即判断value值是否为空，而 ifPresent 就是在 value 值不为空时，做一些操作
 * <p>filter 方法接受一个 Predicate 来对 Optional 中包含的值进行过滤，如果包含的值满足条件，那么还是返回这个 Optional；否则返回 Optional.empty
 *
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 * @since 1.0.0
 */
public class PresentDemo {

    public static void main(String[] args) {
        User user = new User();
        // 千万不要把:
        if (user != null) {
            // TODO: do something
        }
        //给写成:
        Optional<User> user1 = Optional.ofNullable(user);
        if (user1.isPresent()) {
            // TODO: do something
        }
        // 正确写法:
        Optional.ofNullable(user)
                .ifPresent(u -> {
                    // dosomething(u);
                });

        Optional.ofNullable(user).ifPresent(u -> {
            // TODO: do something
        });

        Optional<User> user2 = Optional.ofNullable(user).filter(u -> u.getPassword().length() < 6);
    }


}
