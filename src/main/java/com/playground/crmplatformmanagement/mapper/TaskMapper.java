package com.playground.crmplatformmanagement.mapper;

import com.playground.crmplatformmanagement.dto.TaskDto;
import com.playground.crmplatformmanagement.entities.TaskProcessorEntity;

import static com.playground.crmplatformmanagement.constants.TaskStatusEnum.PROCESSING;

public class TaskMapper {
    public static TaskDto toDto(TaskProcessorEntity entity) {
        return TaskDto.builder()
                .taskId(entity.getTaskId())
                .taskName(entity.getTaskName())
                .source(entity.getSource())
                .destination(entity.getDestination())
                .createdOn(entity.getCreatedOn())
                .updatedOn(entity.getUpdatedOn())
                .build();
    }

    public static TaskProcessorEntity toEntity(TaskDto dto) {
        TaskProcessorEntity entity = new TaskProcessorEntity();
        entity.setTaskId(dto.getTaskId()); // optional for update
        entity.setTaskName(dto.getTaskName());
        entity.setSource(dto.getSource());
        entity.setDestination(dto.getDestination());
        entity.setStatus(PROCESSING.toString()); // default for create
        return entity;
    }
}
