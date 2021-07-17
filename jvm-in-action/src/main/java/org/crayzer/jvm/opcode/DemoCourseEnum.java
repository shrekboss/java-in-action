package org.crayzer.jvm.opcode;

import javax.validation.ValidationException;
public enum DemoCourseEnum {
    JAVA(1L, "java", "Java进阶训练营"),
    PYTHON(2L, "python", "Python进阶训练营"),
    GOLANG(3L, "go", "Go进阶训练营"),
    ;
    public final Long id;
    public final String code; // 编码
    public final String desc; // 说明
    DemoCourseEnum(Long id, String code, String desc) {
        this.id = id;
        this.code = code;
        this.desc = desc;
    }
    public static DemoCourseEnum check(String theCode) {
        for (DemoCourseEnum item : DemoCourseEnum.values()) {
            if (item.code.equalsIgnoreCase(theCode)) {
                return item;
            }
        }
        throw new ValidationException("不支持的类型：" + theCode);
    }
    public static Long code2id(String theCode) {
        for (DemoCourseEnum item : DemoCourseEnum.values()) {
            if (item.code.equalsIgnoreCase(theCode)) {
                return item.id;
            }
        }
        return null;
    }
}
