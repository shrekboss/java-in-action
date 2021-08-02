package org.crayzer.spring.conversion;

import org.crayzer.spring.ioc.domain.User;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

/**
 * 自定义 {@link PropertyEditorRegistrar} 实现
 *
 * @author crayzer
 * @see PropertyEditorRegistrar
 */
// @Component // 3. 将其声明为 Spring Bean
public class CustomizedPropertyEditorRegistrar implements PropertyEditorRegistrar {

    @Override
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        // 1. 通用类型转换
        // 2. Java Bean 属性类型转换
        registry.registerCustomEditor(User.class, "context", new StringToPropertiesPropertyEditor());
    }
}
