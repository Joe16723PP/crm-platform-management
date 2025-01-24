package com.playground.crmplatformmanagement.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class TaskDto {
    private Long taskId;
    private String source;
    private String destination;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    // validation example
    @NotBlank(message = "Task name is required")
    @Size(max = 100, message = "Task name cannot exceed 100 characters")
    private String taskName;
}
