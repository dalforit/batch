package com.ip.manager.utils.enums;

public enum SeverityEnum {
	
    LOW("LOW"),
    MEDIUM("MEDIUM"),
    HIGH("HIGH"),
    CRITICAL("CRITICAL");

    private final String value;

    private SeverityEnum(String value) {
        this.value = value;
    }

    public static SeverityEnum fromValue(String value) {
        for (SeverityEnum severity : SeverityEnum.values()) {
            if (severity.value.equalsIgnoreCase(value)) {
                return severity;
            }
        }
        throw new IllegalArgumentException("Invalid SeverityEnum value: " + value);
    }

    @Override
    public String toString() {
        return value;
    }
}
