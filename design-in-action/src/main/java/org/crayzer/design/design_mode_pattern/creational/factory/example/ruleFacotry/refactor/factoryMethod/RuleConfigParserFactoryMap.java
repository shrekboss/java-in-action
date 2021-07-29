package org.crayzer.design.design_mode_pattern.creational.factory.example.ruleFacotry.refactor.factoryMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yizhe.chen
 */
//因为工厂类只包含方法，不包含成员变量，完全可以复用，
//不需要每次都创建新的工厂类对象，所以，简单工厂模式的第二种实现思路更加合适。
//工厂的工厂
public class RuleConfigParserFactoryMap {
    private static final Map<String, IRuleConfigParserFactory> CACHED_FACTORIES = new HashMap<>();

    static {
        CACHED_FACTORIES.put("json", new JsonRuleConfigParserFactory());
        CACHED_FACTORIES.put("xml", new XmlRuleConfigParserFactory());
        CACHED_FACTORIES.put("yaml", new YamlRuleConfigParserFactory());
        CACHED_FACTORIES.put("properties", new PropertiesRuleConfigParserFactory());
    }

    public static IRuleConfigParserFactory getParserFactory(String type) {
        if (type == null || type.isEmpty()) {
            return null;
        }
        IRuleConfigParserFactory parserFactory = CACHED_FACTORIES.get(type.toLowerCase());
        return parserFactory;
    }
}