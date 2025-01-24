package com.playground.crmplatformmanagement.services;

import com.playground.crmplatformmanagement.clients.TaskHandlerClient;
import com.playground.crmplatformmanagement.repositories.TaskProcessorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskProcessorRepository taskProcessorRepository;

    @Mock
    private TaskHandlerClient taskHandlerClient;

    @Test
    void testCreateTask() {
    }

    @Test
    void testUpdateTaskStatus() {
    }
}