package com.playground.crmplatformmanagement.controllers;

import com.playground.crmplatformmanagement.services.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {
    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    @Test
    void testCreateTaskGivenValidRequestShouldReturnSuccess() {
    }

    @Test
    void testHandleWebhookGivenValidRequestShouldReturnSuccess() {
    }
}