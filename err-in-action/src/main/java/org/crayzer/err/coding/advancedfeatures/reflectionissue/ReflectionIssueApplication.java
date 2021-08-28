package org.crayzer.err.coding.advancedfeatures.reflectionissue;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 */
@Slf4j
public class ReflectionIssueApplication {

    public static void main(String[] args) throws Exception {
        ReflectionIssueApplication application = new ReflectionIssueApplication();
        application.wrong();
        System.out.println("===========华丽分割线================");
        application.right();
    }

    private void age(int age) {
        log.info("int age = {}", age);
    }

    private void age(Integer age) {
        log.info("Integer age = {}", age);
    }

    /**
     * 反射调用方法，是以反射获取方法时传入的方法名称和参数类型来确定调用方法的。*/
    public void wrong() throws Exception {
        //  - int age = 36
        getClass().getDeclaredMethod("age", Integer.TYPE).invoke(this, Integer.valueOf("36"));
        getClass().getDeclaredMethod("age", Integer.TYPE).invoke(this, 36);
    }

    public void right() throws Exception {
        // - Integer age = 36
        getClass().getDeclaredMethod("age", Integer.class).invoke(this, Integer.valueOf("36"));
        getClass().getDeclaredMethod("age", Integer.class).invoke(this, 36);
    }
}
