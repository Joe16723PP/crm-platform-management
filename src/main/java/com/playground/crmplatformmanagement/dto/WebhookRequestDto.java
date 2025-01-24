package com.playground.crmplatformmanagement.dto;

import com.playground.crmplatformmanagement.constants.TaskStatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WebhookRequestDto {
    @NotNull(message = "Task id is required")
    private Long taskId;
    private String status;
}
