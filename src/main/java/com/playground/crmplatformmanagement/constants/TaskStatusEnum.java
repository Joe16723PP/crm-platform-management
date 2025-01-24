package com.playground.crmplatformmanagement.constants;

import lombok.Getter;

@Getter
public enum TaskStatusEnum {
    PROCESSING("Processing"),
    COMPLETED("Completed"),
    FAILED("Failed");

    private final String value;

    TaskStatusEnum(String value) {
        this.value = value;
    }

    public static TaskStatusEnum fromValue(String value) {
        for (TaskStatusEnum status : TaskStatusEnum.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid task status: " + value);
    }
}
