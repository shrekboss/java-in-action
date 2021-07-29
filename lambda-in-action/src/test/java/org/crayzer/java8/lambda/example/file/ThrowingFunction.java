package org.crayzer.java8.lambda.example.file;

import java.util.function.Function;

/**
 * 分享一个小技巧吧。因为 Files.readAllLines 方法会抛出一个受检异常（IOException），
 * 所以我使用了一个自定义的函数式接口，用 ThrowingFunction 包装这个方法，把受检异
 * 常转换为运行时异常，让代码更清晰：
 **/
@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Throwable> {
    static <T, R, E extends Throwable> Function<T, R> unchecked(ThrowingFunction<T, R, E> f) {
        return t -> {
            try {
                return f.apply(t);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }

    R apply(T t) throws E;
}
