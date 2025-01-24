package com.playground.crmplatformmanagement.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.playground.crmplatformmanagement.dto.TaskDto;
import com.playground.crmplatformmanagement.dto.WebhookRequestDto;
import com.playground.crmplatformmanagement.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.playground.crmplatformmanagement.constants.TaskStatusEnum.COMPLETED;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {
    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    private MockMvc mockMvc;

    // manual setup MockMvc because using mockito extension instead
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    void testCreateTaskGivenValidRequestShouldReturnSuccess() throws Exception {
        TaskDto dto = TaskDto.builder()
                .taskName("simple task")
                .source("src")
                .destination("desc")
                .build();
        when(taskService.createTask(dto)).thenReturn(true);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto)))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void testCreateTaskGivenInValidRequestShouldReturnFailed() throws Exception {

        TaskDto dto = TaskDto.builder()
                .source("src")
                .destination("desc")
                .build();

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testCreateTaskGivenValidRequestButExceptionShouldReturnFailed() throws Exception {

        TaskDto dto = TaskDto.builder()
                .taskName("simple task")
                .source("src")
                .destination("desc")
                .build();
        when(taskService.createTask(dto)).thenReturn(false);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto)))
                .andExpect(status().isInternalServerError())
                .andReturn();
    }


    @Test
    void testHandleWebhookGivenValidRequestShouldReturnSuccess() throws Exception {
        WebhookRequestDto dto = new WebhookRequestDto();
        dto.setTaskId(1L);
        dto.setStatus(COMPLETED.toString());
        when(taskService.updateTaskStatus(any())).thenReturn(true);

        mockMvc.perform(post("/api/tasks/webhook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto)))
                .andExpect(status().isAccepted())
                .andReturn();
    }

    // the rest should follow the same pattern about validating service response and req

    private String toJsonString(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}