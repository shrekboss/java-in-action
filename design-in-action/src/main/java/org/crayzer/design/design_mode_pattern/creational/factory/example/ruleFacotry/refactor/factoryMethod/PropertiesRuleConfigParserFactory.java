package org.crayzer.design.design_mode_pattern.creational.factory.example.ruleFacotry.refactor.factoryMethod;

import org.crayzer.design.design_mode_pattern.creational.factory.example.ruleFacotry.IRuleConfigParser;
import org.crayzer.design.design_mode_pattern.creational.factory.example.ruleFacotry.PropertiesRuleConfigParser;

public class PropertiesRuleConfigParserFactory implements IRuleConfigParserFactory {
    @Override
    public IRuleConfigParser createParser() {
        return new PropertiesRuleConfigParser();
    }
}
