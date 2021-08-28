package org.crayzer.err.coding.advancedfeatures.innerclass;

/**
 * Synthetic 方法是编译器自动生成的方法（在源码中不出现）。
 * 除了提到的泛型类型擦除外，Synthetic 方法还可能出现的一个比较常
 * 见的场景，是内部类和顶层类需要相互访问对方的 private 字段或方法
 * 的时候。
 *
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 */
public class InnerClassApplication {

    private String gender = "male";

    public static void main(String[] args) throws Exception {

        InnerClassApplication application = new InnerClassApplication();
        application.test();
    }

    private void test() {
        MyInnerClass myInnerClass = new MyInnerClass();
        System.out.println(myInnerClass.name);
        myInnerClass.test();
    }

    class MyInnerClass {
        private String name = "crayzer";

        void test() {
            System.out.println(gender);
        }
    }
}
