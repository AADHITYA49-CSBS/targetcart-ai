package com.targetcart.ai.modules.execution.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.targetcart.ai.common.error.ResourceNotFoundException;
import com.targetcart.ai.modules.execution.dto.ExecutionLogDto;
import com.targetcart.ai.modules.execution.repository.ExecutionLogRepository;

@Service
@Transactional(readOnly = true)
public class ExecutionLogService {

    private final ExecutionLogRepository executionLogRepository;

    public ExecutionLogService(ExecutionLogRepository executionLogRepository) {
        this.executionLogRepository = executionLogRepository;
    }

    public List<ExecutionLogDto> findAll() {
        return executionLogRepository.findAll().stream()
                .map(ExecutionLogDto::from)
                .toList();
    }

    public ExecutionLogDto findById(Long id) {
        return executionLogRepository.findById(id)
                .map(ExecutionLogDto::from)
                .orElseThrow(() -> ResourceNotFoundException.of("ExecutionLog", id));
    }
}