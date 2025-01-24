package com.playground.crmplatformmanagement.repositories;

import com.playground.crmplatformmanagement.entities.TaskProcessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskProcessorRepository extends JpaRepository<TaskProcessorEntity, Long> {
    @Modifying
    @Query("update TaskProcessorEntity set status = :status where taskId = :taskId")
    int updateTaskStatusById(@Param("taskId") Long taskId, @Param("status") String status);
}
