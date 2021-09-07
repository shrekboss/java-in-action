package org.crayzer.err.coding.springpart1.aopmetrics;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Metrics {
    /**
     * 是否在成功执行方法后打点
     */
    boolean recordSuccessMetrics() default true;

    /**
     * 是否在执行方法出错时打点
     */
    boolean recordFailMetrics() default true;

    /**
     * 是否记录请求参数
     */
    boolean logParameters() default true;

    /**
     * 是否记录返回值
     */
    boolean logReturn() default true;

    /**
     * 是否记录异常
     */
    boolean logException() default true;

    /**
     * 是否屏蔽异常返回默认值
     */
    boolean ignoreException() default false;
}
