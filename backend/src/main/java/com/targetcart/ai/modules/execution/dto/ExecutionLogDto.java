package com.targetcart.ai.modules.execution.dto;

import java.time.LocalDateTime;

import com.targetcart.ai.modules.execution.entity.ExecutionLog;
import com.targetcart.ai.modules.execution.entity.ExecutionLogStatus;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Full representation of an execution log entry as returned by the
 * Execution Logs API. References the campaign by id only, avoiding the
 * campaign object graph entirely.
 */
@Schema(name = "ExecutionLog", description = "A record of one campaign execution attempt")
public record ExecutionLogDto(
        @Schema(description = "Unique execution log identifier") Long id,
        @Schema(description = "Identifier of the campaign this log belongs to") Long campaignId,
        @Schema(description = "Outcome of the execution") ExecutionLogStatus status,
        @Schema(description = "When the execution started") LocalDateTime startedAt,
        @Schema(description = "When the execution finished, if it did") LocalDateTime completedAt,
        @Schema(description = "Total execution time in milliseconds") Integer durationMs,
        @Schema(description = "AI model used, if any") String modelName,
        @Schema(description = "Prompt tokens consumed, if tracked") Integer inputTokens,
        @Schema(description = "Completion tokens produced, if tracked") Integer outputTokens,
        @Schema(description = "Error detail when the execution failed") String errorMessage,
        @Schema(description = "When the log was created") LocalDateTime createdAt) {

    public static ExecutionLogDto from(ExecutionLog log) {
        return new ExecutionLogDto(
                log.getId(),
                log.getCampaign().getId(),
                log.getStatus(),
                log.getStartedAt(),
                log.getCompletedAt(),
                log.getDurationMs(),
                log.getModelName(),
                log.getInputTokens(),
                log.getOutputTokens(),
                log.getErrorMessage(),
                log.getCreatedAt());
    }
}