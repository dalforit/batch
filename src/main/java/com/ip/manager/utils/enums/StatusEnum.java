package com.ip.manager.utils.enums;

public enum StatusEnum {
	
    INVESTIGATION("INVESTIGATION"),
    WARNING("WARNING"),
    BLOCKED("BLOCKED"),
    BLACKLISTED("BLACKLISTED");

    private final String value;

    private StatusEnum(String value) {
        this.value = value;
    }

    public static StatusEnum fromValue(String value) {
        for (StatusEnum severity : StatusEnum.values()) {
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
