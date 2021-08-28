package org.crayzer.err.coding.advancedfeatures.annotationinheritance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotatedElementUtils;

/**
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 */
@Slf4j
public class AnnotationInheritanceApplication {

    public static void main(String[] args) throws NoSuchMethodException {
        wrong();
        System.out.println();
        right();
    }

    private static String getAnnotationValue(MyAnnotation annotation) {
        if (annotation == null) return "";
        return annotation.value();
    }

    public static void wrong() throws NoSuchMethodException {
        Parent parent = new Parent();
        log.info("ParentClass:{}", getAnnotationValue(parent.getClass().getAnnotation(MyAnnotation.class)));
        log.info("ParentMethod:{}", getAnnotationValue(parent.getClass().getMethod("foo")
                .getAnnotation(MyAnnotation.class)));

        Child child = new Child();
        log.info("ChildClass:{}", getAnnotationValue(child.getClass().getAnnotation(MyAnnotation.class)));
        log.info("ChildMethod:{}", getAnnotationValue(child.getClass().getMethod("foo")
                .getAnnotation(MyAnnotation.class)));
    }

    public static void right() throws NoSuchMethodException {
        Parent parent = new Parent();
        log.info("ParentClass:{}", getAnnotationValue(parent.getClass().getAnnotation(MyAnnotation.class)));
        log.info("ParentMethod:{}", getAnnotationValue(parent.getClass().getMethod("foo")
                .getAnnotation(MyAnnotation.class)));

        Child child = new Child();
        // Spring 提供了 AnnotatedElementUtils 类，区别 findXXX 和 getXXX。
        log.info("ChildClass: getMergedAnnotation {}", getAnnotationValue(
                AnnotatedElementUtils.getMergedAnnotation(child.getClass(),
                        MyAnnotation.class)));
        log.info("ChildMethod: getMergedAnnotation {}", getAnnotationValue(
                AnnotatedElementUtils.getMergedAnnotation(child.getClass().getMethod("foo"),
                        MyAnnotation.class)));

        log.info("ChildClass: findMergedAnnotation {}", getAnnotationValue(
                AnnotatedElementUtils.findMergedAnnotation(child.getClass(),
                        MyAnnotation.class)));
        log.info("ChildMethod: findMergedAnnotation {}", getAnnotationValue(
                AnnotatedElementUtils.findMergedAnnotation(child.getClass().getMethod("foo"),
                        MyAnnotation.class)));
    }

    @MyAnnotation(value = "Class")
    @Slf4j
    static class Parent {

        @MyAnnotation(value = "Method")
        public void foo() {
        }
    }

    @Slf4j
    static class Child extends Parent {
        @Override
        public void foo() {
        }
    }
}

