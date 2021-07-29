package org.crayzer.design.design_mode_principle.ocp;

/**
 * @author yizhe.chen
 */
public enum NotificationEmergencyLevel {

    /**
     *
     */
    SEVERE(1, "严重"),
    URGENCY(2, "紧急"),
    NORMAL(3, "普通"),
    TRIVIAL(4, "无关紧要");

    public final int key;
    public final String desc;

    NotificationEmergencyLevel(int key, String desc) {
        this.desc = desc;
        this.key = key;
    }

    public static NotificationEmergencyLevel getEnumByKey(int key) {
        NotificationEmergencyLevel[]  enumValues = values();
        for (NotificationEmergencyLevel enumValue : enumValues) {
            if (enumValue.key == key) {
                return enumValue;
            }
        }
        throw new RuntimeException("Error: Unknown key, or do not support type!");
    }
}
