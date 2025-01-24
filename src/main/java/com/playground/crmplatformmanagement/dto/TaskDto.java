package com.playground.crmplatformmanagement.dto;


import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class TaskDto {
    private Long taskId;
    private String taskName;
    private String source;
    private String destination;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
