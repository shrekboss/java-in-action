package org.crayzer.spring.conversion;

import java.beans.PropertyEditor;

/**
 * {@link PropertyEditor} 示例
 *
 * @author crayzer
 * @see PropertyEditor
 */
public class PropertyEditorDemo {

    public static void main(String[] args) {

        // 模拟 Spring Framework 操作
        // 有一段文本 name = 马克图布;
        String text = "name = 马克图布";

        PropertyEditor propertyEditor = new StringToPropertiesPropertyEditor();
        // 传递 String 类型的内容
        propertyEditor.setAsText(text);

        System.out.println(propertyEditor.getValue());

        System.out.println(propertyEditor.getAsText());
    }
}
