package org.crayzer.design.design_mode_pattern.creational.factory.example.ruleFacotry.refactor.factoryMethod;


import org.crayzer.design.design_mode_pattern.creational.factory.example.InvalidRuleConfigException;
import org.crayzer.design.design_mode_pattern.creational.factory.example.ruleFacotry.IRuleConfigParser;
import org.crayzer.design.design_mode_pattern.creational.factory.example.ruleFacotry.RuleConfig;

public class RuleConfigSource {
    public RuleConfig load(String ruleConfigFilePath) {
        String ruleConfigFileExtension = getFileExtension(ruleConfigFilePath);

        IRuleConfigParserFactory parserFactory = RuleConfigParserFactoryMap.getParserFactory(ruleConfigFileExtension);
        if (parserFactory == null) {
            throw new InvalidRuleConfigException("Rule config file format is not supported: " + ruleConfigFilePath);
        }
        IRuleConfigParser parser = parserFactory.createParser();

        String configText = "";
        //从ruleConfigFilePath文件中读取配置文本到configText中
        RuleConfig ruleConfig = parser.parse(configText);
        return ruleConfig;
    }

    private String getFileExtension(String filePath) {
        //...解析文件名获取扩展名，比如rule.json，返回json
        return "json";
    }
}
