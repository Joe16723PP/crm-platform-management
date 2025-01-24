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
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        try {
            taskService.createTask(taskDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception ignored) {
            // TODO: differentiated error base on error type
            return ResponseEntity.internalServerError().build();
        }

    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(@RequestBody TaskDto webhookRequest) {
        taskService.updateTask(webhookRequest);
        return ResponseEntity.ok().build();
    }
}
