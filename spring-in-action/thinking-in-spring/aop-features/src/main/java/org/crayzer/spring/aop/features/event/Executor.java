
package org.crayzer.spring.aop.features.event;

/**
 * @author crayzer
 */
public class Executor { // ClassFilter

    public void execute() { // MethodMatcher: Join Point 方法（需要 Pointcut 来匹配）
        System.out.println("Executing...");
    }
}
