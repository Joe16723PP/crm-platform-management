package com.playground.crmplatformmanagement.controllers;

import com.playground.crmplatformmanagement.dto.TaskDto;
import com.playground.crmplatformmanagement.dto.WebhookRequestDto;
import com.playground.crmplatformmanagement.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskDto taskDto) {
        boolean isSuccess = taskService.createTask(taskDto);
        return isSuccess ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.internalServerError().build();
    }

    @PostMapping("/webhook")
    public ResponseEntity<?> handleWebhook(@RequestBody WebhookRequestDto webhookRequest) {
        boolean isSuccess = taskService.updateTaskStatus(webhookRequest);
        return isSuccess ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.internalServerError().build();
    }
}
