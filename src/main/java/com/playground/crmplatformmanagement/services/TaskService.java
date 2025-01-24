package com.playground.crmplatformmanagement.services;


import com.playground.crmplatformmanagement.clients.TaskHandlerClient;
import com.playground.crmplatformmanagement.dto.TaskDto;
import com.playground.crmplatformmanagement.dto.WebhookRequestDto;
import com.playground.crmplatformmanagement.dto.taskhandlerclient.TaskHandlerRequestDto;
import com.playground.crmplatformmanagement.dto.taskhandlerclient.TaskHandlerResponseDto;
import com.playground.crmplatformmanagement.entities.TaskProcessorEntity;
import com.playground.crmplatformmanagement.mapper.TaskMapper;
import com.playground.crmplatformmanagement.repositories.TaskProcessorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.playground.crmplatformmanagement.utils.TaskUtils.isValidTaskStatus;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskProcessorRepository taskProcessorRepository;
    private final TaskHandlerClient taskHandlerClient;

    public boolean createTask(TaskDto taskDto) {
        try {
            TaskProcessorEntity entity = TaskMapper.toEntity(taskDto);
            TaskProcessorEntity dbEntity = taskProcessorRepository.save(entity);
            TaskHandlerRequestDto dto = new TaskHandlerRequestDto();
            dto.setTaskId(dbEntity.getTaskId());
            dto.setType(dbEntity.getTaskName());
            TaskHandlerResponseDto resp = taskHandlerClient.executeTask(dto);
            return resp.isStatus();
        } catch (Exception e) {
            log.error("createTask error: ", e);
            return false;
        }
    }

    public boolean updateTaskStatus(WebhookRequestDto req) {
        if (!taskProcessorRepository.existsById(req.getTaskId())) {
            throw new RuntimeException("Task not found with ID: " + req.getTaskId());
        }
        if (isValidTaskStatus(req.getStatus().toLowerCase())) {
            int updated = taskProcessorRepository.updateTaskStatusById(req.getTaskId(), req.getStatus());
            return updated > 0;
        } else {
            throw new RuntimeException("invalid task status: " + req.getTaskId());
        }
    }

    public void updateTask(TaskDto taskDto) {
        if (!taskProcessorRepository.existsById(taskDto.getTaskId())) {
            throw new RuntimeException("Task not found with ID: " + taskDto.getTaskId());
        }
        TaskProcessorEntity entity = TaskMapper.toEntity(taskDto);
        try {
            taskProcessorRepository.save(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error updating Task: " + taskDto.getTaskId(), e);
        }
    }

    // other CRUD operations
    public List<TaskProcessorEntity> getAllTasks() {
        return taskProcessorRepository.findAll();
    }

    public TaskProcessorEntity getTaskById(Long taskId) {
        return taskProcessorRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));
    }

    public void deleteTask(Long taskId) {
        taskProcessorRepository.deleteById(taskId);
    }
}
