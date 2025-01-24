package com.playground.crmplatformmanagement.services;

import com.playground.crmplatformmanagement.clients.TaskHandlerClient;
import com.playground.crmplatformmanagement.dto.TaskDto;
import com.playground.crmplatformmanagement.dto.WebhookRequestDto;
import com.playground.crmplatformmanagement.dto.taskhandlerclient.TaskHandlerRequestDto;
import com.playground.crmplatformmanagement.dto.taskhandlerclient.TaskHandlerResponseDto;
import com.playground.crmplatformmanagement.entities.TaskProcessorEntity;
import com.playground.crmplatformmanagement.repositories.TaskProcessorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.playground.crmplatformmanagement.constants.TaskStatusEnum.COMPLETED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskProcessorRepository taskProcessorRepository;

    @Mock
    private TaskHandlerClient taskHandlerClient;

    @Test
    void testCreateTaskGivenValidRequestShouldReturnTrue() {

        TaskDto taskDto = TaskDto.builder()
                .taskName("simple task")
                .source("src")
                .destination("desc")
                .build();

        TaskProcessorEntity savedEntity = new TaskProcessorEntity();
        savedEntity.setTaskId(1L);
        savedEntity.setTaskName("Sample Task");

        when(taskProcessorRepository.save(Mockito.any(TaskProcessorEntity.class))).thenReturn(savedEntity);

        TaskHandlerResponseDto handlerResponse = new TaskHandlerResponseDto();
        handlerResponse.setStatus(true);

        when(taskHandlerClient.executeTask(Mockito.any(TaskHandlerRequestDto.class))).thenReturn(handlerResponse);

        // ack
        boolean result = taskService.createTask(taskDto);

        assertTrue(result);
        verify(taskProcessorRepository, Mockito.times(1)).save(Mockito.any(TaskProcessorEntity.class));
        verify(taskHandlerClient, Mockito.times(1)).executeTask(Mockito.any(TaskHandlerRequestDto.class));

    }

    @Test
    void testCreateTaskGivenExceptionShouldReturnFalse() {
        TaskDto taskDto = TaskDto.builder()
                .taskName("simple task")
                .source("src")
                .destination("desc")
                .build();

        when(taskProcessorRepository.save(Mockito.any(TaskProcessorEntity.class)))
                .thenThrow(new RuntimeException("Database error"));

        boolean result = taskService.createTask(taskDto);

        Assertions.assertFalse(result);
        Mockito.verify(taskProcessorRepository, Mockito.times(1)).save(Mockito.any(TaskProcessorEntity.class));
        Mockito.verifyNoInteractions(taskHandlerClient); // No interaction with Feign client since repository fails
    }

    @Test
    void testUpdateTaskStatusGivenValidRequestShouldReturnTrue() {
        WebhookRequestDto req = new WebhookRequestDto();
        req.setTaskId(1L);
        req.setStatus(COMPLETED.toString());

        when(taskProcessorRepository.existsById(req.getTaskId())).thenReturn(true);
        when(taskProcessorRepository.updateTaskStatusById(req.getTaskId(), req.getStatus())).thenReturn(1);

        boolean result = taskService.updateTaskStatus(req);

        Assertions.assertTrue(result);
        Mockito.verify(taskProcessorRepository, Mockito.times(1)).existsById(req.getTaskId());
        Mockito.verify(taskProcessorRepository, Mockito.times(1)).updateTaskStatusById(req.getTaskId(), req.getStatus());
    }

    @Test
    void testUpdateTaskStatusGivenInvalidStatusShouldThrowException() {
        WebhookRequestDto req = new WebhookRequestDto();
        req.setTaskId(1L);
        req.setStatus("invalid_status");

        when(taskProcessorRepository.existsById(req.getTaskId())).thenReturn(true);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            taskService.updateTaskStatus(req);
        });

        Assertions.assertEquals("invalid task status: 1", exception.getMessage());
        Mockito.verify(taskProcessorRepository, Mockito.times(1)).existsById(req.getTaskId());
        Mockito.verify(taskProcessorRepository, Mockito.never()).updateTaskStatusById(Mockito.anyLong(), Mockito.anyString());
    }

    @Test
    void testUpdateTaskStatusGivenNonExistingTaskShouldThrowException() {
        WebhookRequestDto req = new WebhookRequestDto();
        req.setTaskId(1L);
        req.setStatus("invalid_status");

        when(taskProcessorRepository.existsById(req.getTaskId())).thenReturn(false);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            taskService.updateTaskStatus(req);
        });

        Assertions.assertEquals("Task not found with ID: 1", exception.getMessage());
        Mockito.verify(taskProcessorRepository, Mockito.times(1)).existsById(req.getTaskId());
        Mockito.verify(taskProcessorRepository, Mockito.never()).updateTaskStatusById(Mockito.anyLong(), Mockito.anyString());
    }
}