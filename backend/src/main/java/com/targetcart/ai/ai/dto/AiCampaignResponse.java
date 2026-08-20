package com.targetcart.ai.ai.dto;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response DTO for AI campaign generation.
 * Contains the generated campaign subject, content, and personalization rationale.
 */
@Schema(name = "AiCampaignResponse", description = "AI-generated campaign output")
public record AiCampaignResponse(
        @Schema(description = "Generated email subject line") String subject,
        @Schema(description = "Generated email body/content") String content,
        @Schema(description = "Brief explanation of personalization choices made") String personalizationRationale,
        @Schema(description = "AI model used for generation") String model,
        @Schema(description = "Timestamp of generation") Instant generatedAt,
        @Schema(description = "Input tokens consumed") Integer inputTokens,
        @Schema(description = "Output tokens generated") Integer outputTokens) {
}