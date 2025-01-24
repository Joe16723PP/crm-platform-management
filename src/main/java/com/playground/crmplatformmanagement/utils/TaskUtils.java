package com.playground.crmplatformmanagement.utils;

import com.playground.crmplatformmanagement.constants.TaskStatusEnum;

public class TaskUtils {

    public static boolean isValidTaskStatus(String status) {
        try {
            TaskStatusEnum.valueOf(status);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
