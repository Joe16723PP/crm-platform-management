package com.playground.crmplatformmanagement.clients;

import com.playground.crmplatformmanagement.configs.FeignClientConfig;
import com.playground.crmplatformmanagement.dto.taskhandlerclient.TaskHandlerRequestDto;
import com.playground.crmplatformmanagement.dto.taskhandlerclient.TaskHandlerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "task-handler-client",
        url = "${microservice.task-handler-service.url}",
        configuration = FeignClientConfig.class)
public interface TaskHandlerClient {

    @PostMapping("/api/process/do-something")
    TaskHandlerResponseDto executeTask(@RequestBody TaskHandlerRequestDto taskHandlerRequestDto);
}
