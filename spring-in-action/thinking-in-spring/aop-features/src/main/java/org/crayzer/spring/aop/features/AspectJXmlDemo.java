
package org.crayzer.spring.aop.features;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author crayzer
 */
@Aspect        // 声明为 Aspect 切面
@Configuration // Configuration class
public class AspectJXmlDemo {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:/META-INF/spring-aop-xml-context.xml");

        AspectJXmlDemo aspectJXml = context.getBean("aspectJXmlDemo", AspectJXmlDemo.class);

        context.close();
    }
}
