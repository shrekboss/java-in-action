package org.crayzer.spring.annotation.enable;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * HelloWorld 模块 {@link ImportSelector} 实现
 *
 * @see ImportSelector
 */
public class HelloWorldImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"org.crayzer.spring.annotation.enable.HelloWorldConfiguration"}; // 导入
    }
}
