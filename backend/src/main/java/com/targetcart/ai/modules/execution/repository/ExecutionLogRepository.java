package com.targetcart.ai.modules.execution.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.targetcart.ai.modules.execution.entity.ExecutionLog;
import com.targetcart.ai.modules.execution.entity.ExecutionLogStatus;

public interface ExecutionLogRepository extends JpaRepository<ExecutionLog, Long> {

    List<ExecutionLog> findByCampaignId(Long campaignId);

    List<ExecutionLog> findByStatus(ExecutionLogStatus status);
}
