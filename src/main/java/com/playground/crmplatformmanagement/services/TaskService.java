package com.playground.crmplatformmanagement.services;


import com.playground.crmplatformmanagement.dto.TaskDto;
import com.playground.crmplatformmanagement.entities.TaskProcessorEntity;
import com.playground.crmplatformmanagement.mapper.TaskMapper;
import com.playground.crmplatformmanagement.repositories.TaskProcessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskProcessorRepository taskProcessorRepository;

    public void createTask(TaskDto taskDto) {
        try {
            TaskProcessorEntity entity = TaskMapper.toEntity(taskDto);
            taskProcessorRepository.save(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error creating task", e);
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
