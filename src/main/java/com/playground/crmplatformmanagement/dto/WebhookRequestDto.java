package com.playground.crmplatformmanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WebhookRequestDto {
    Long taskId;
    String status;
}
