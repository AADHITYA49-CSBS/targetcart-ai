package com.targetcart.ai.ai.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests for AI campaign DTO construction and serialization.
 */
class AiCampaignDtoTest {

    @Test
    void aiCampaignRequest_constructsWithAllFields() {
        AiCampaignRequest request = new AiCampaignRequest(
                1L,
                new AiCampaignRequest.CustomerContext(1L, "test@example.com", "John", "Doe", true, 5, new BigDecimal("100.00")),
                List.of(
                        new AiCampaignRequest.CartItemContext(
                                1L, 2, new BigDecimal("25.00"),
                                new AiCampaignRequest.ProductContext(1L, "SKU-001", "Test Product", "Description", "Category", new BigDecimal("25.00")))),
                new BigDecimal("50.00"),
                "ABANDONED",
                "2026-08-15T14:30:00");

        assertThat(request.cartId()).isEqualTo(1L);
        assertThat(request.customer().firstName()).isEqualTo("John");
        assertThat(request.items()).hasSize(1);
        assertThat(request.totalAmount()).isEqualByComparingTo("50.00");
        assertThat(request.cartStatus()).isEqualTo("ABANDONED");
    }

    @Test
    void aiCampaignResponse_constructsWithAllFields() {
        Instant now = Instant.now();
        AiCampaignResponse response = new AiCampaignResponse(
                "Test Subject",
                "Test Content",
                "Test Rationale",
                "gpt-4o-mini",
                now,
                100,
                200);

        assertThat(response.subject()).isEqualTo("Test Subject");
        assertThat(response.content()).isEqualTo("Test Content");
        assertThat(response.personalizationRationale()).isEqualTo("Test Rationale");
        assertThat(response.model()).isEqualTo("gpt-4o-mini");
        assertThat(response.generatedAt()).isEqualTo(now);
        assertThat(response.inputTokens()).isEqualTo(100);
        assertThat(response.outputTokens()).isEqualTo(200);
    }

    @Test
    void aiCampaignResponse_allowsNullOptionalFields() {
        AiCampaignResponse response = new AiCampaignResponse(
                "Subject", "Content", null, "model", Instant.now(), null, null);

        assertThat(response.personalizationRationale()).isNull();
        assertThat(response.inputTokens()).isNull();
        assertThat(response.outputTokens()).isNull();
    }
}