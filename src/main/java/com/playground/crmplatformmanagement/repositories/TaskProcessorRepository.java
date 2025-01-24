package com.playground.crmplatformmanagement.repositories;

import com.playground.crmplatformmanagement.entities.TaskProcessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskProcessorRepository extends JpaRepository<TaskProcessorEntity, Long> {
}
