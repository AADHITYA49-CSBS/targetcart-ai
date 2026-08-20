package com.targetcart.ai.ai.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.targetcart.ai.ai.dto.AiCampaignRequest;
import com.targetcart.ai.ai.dto.AiCampaignResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.Builder;

/**
 * Unit tests for {@link OpenAiCampaignService} focusing on prompt construction
 * and response parsing logic.
 */
class OpenAiCampaignServiceTest {

    private OpenAiCampaignService service;
    private Builder chatClientBuilder;

    @BeforeEach
    void setUp() {
        chatClientBuilder = mock(Builder.class);
        when(chatClientBuilder.build()).thenReturn(mock(ChatClient.class));
        service = new OpenAiCampaignService(chatClientBuilder);
    }

    @Test
    void parseResponse_validJson_returnsStructuredResponse() {
        String mockResponse = """
                {
                  "subject": "Alice, your cart is waiting!",
                  "content": "Hi Alice, we noticed you left some items in your cart...",
                  "personalizationRationale": "Used first name and referenced specific products (Wireless Bluetooth Headphones, Portable Power Bank) from the Electronics category."
                }
                """;

        AiCampaignResponse response = invokeParseResponse(mockResponse, buildSampleRequest());

        assertThat(response.subject()).isEqualTo("Alice, your cart is waiting!");
        assertThat(response.content()).contains("Hi Alice");
        assertThat(response.personalizationRationale()).contains("Wireless Bluetooth Headphones");
        assertThat(response.model()).isEqualTo("gpt-4o-mini");
        assertThat(response.generatedAt()).isNotNull();
    }

    @Test
    void parseResponse_minimalValidJson_returnsResponseWithDefaults() {
        String mockResponse = """
                {"subject":"Test Subject","content":"Test Content"}
                """;

        AiCampaignResponse response = invokeParseResponse(mockResponse, buildSampleRequest());

        assertThat(response.subject()).isEqualTo("Test Subject");
        assertThat(response.content()).isEqualTo("Test Content");
        assertThat(response.personalizationRationale()).isNotNull();
        assertThat(response.model()).isEqualTo("gpt-4o-mini");
    }

    @Test
    void parseResponse_missingSubject_throwsException() {
        String mockResponse = """
                {"content":"Test Content"}
                """;

        assertThatThrownBy(() -> invokeParseResponse(mockResponse, buildSampleRequest()))
                .isInstanceOf(AiGenerationException.class)
                .hasMessageContaining("missing required fields");
    }

    @Test
    void parseResponse_missingContent_throwsException() {
        String mockResponse = """
                {"subject":"Test Subject"}
                """;

        assertThatThrownBy(() -> invokeParseResponse(mockResponse, buildSampleRequest()))
                .isInstanceOf(AiGenerationException.class)
                .hasMessageContaining("missing required fields");
    }

    @Test
    void parseResponse_invalidJson_throwsException() {
        String mockResponse = "not valid json";

        assertThatThrownBy(() -> invokeParseResponse(mockResponse, buildSampleRequest()))
                .isInstanceOf(AiGenerationException.class)
                .hasMessageContaining("Failed to parse AI response");
    }

    @Test
    void buildUserPrompt_includesCustomerContext() {
        AiCampaignRequest request = buildSampleRequest();
        String prompt = invokeBuildUserPrompt(request);

        assertThat(prompt).contains("Customer Context:");
        assertThat(prompt).contains("Name: Alice Johnson");
        assertThat(prompt).contains("Email: alice.johnson@example.com");
        assertThat(prompt).contains("VIP: true");
        assertThat(prompt).contains("Total Orders: 12");
        assertThat(prompt).contains("Lifetime Spend: $1489.50");
    }

    @Test
    void buildUserPrompt_includesCartContext() {
        AiCampaignRequest request = buildSampleRequest();
        String prompt = invokeBuildUserPrompt(request);

        assertThat(prompt).contains("Cart Context:");
        assertThat(prompt).contains("Cart ID: 1");
        assertThat(prompt).contains("Status: ABANDONED");
        assertThat(prompt).contains("Total Amount: $189.98");
    }

    @Test
    void buildUserPrompt_includesItemDetails() {
        AiCampaignRequest request = buildSampleRequest();
        String prompt = invokeBuildUserPrompt(request);

        assertThat(prompt).contains("Cart Items:");
        assertThat(prompt).contains("Wireless Bluetooth Headphones");
        assertThat(prompt).contains("Electronics");
        assertThat(prompt).contains("Portable Power Bank");
        assertThat(prompt).contains("x1 @ $149.99");
        assertThat(prompt).contains("x1 @ $39.99");
    }

    @Test
    void dtoConstruction_worksCorrectly() {
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
    void dtoConstruction_allowsNullOptionalFields() {
        AiCampaignResponse response = new AiCampaignResponse(
                "Subject", "Content", null, "model", Instant.now(), null, null);

        assertThat(response.personalizationRationale()).isNull();
        assertThat(response.inputTokens()).isNull();
        assertThat(response.outputTokens()).isNull();
    }

    private AiCampaignRequest buildSampleRequest() {
        return new AiCampaignRequest(
                1L,
                new AiCampaignRequest.CustomerContext(
                        1L, "alice.johnson@example.com", "Alice", "Johnson",
                        true, 12, new BigDecimal("1489.50")),
                List.of(
                        new AiCampaignRequest.CartItemContext(
                                1L, 1, new BigDecimal("149.99"),
                                new AiCampaignRequest.ProductContext(
                                        1L, "ELEC-001", "Wireless Bluetooth Headphones",
                                        "Noise-cancelling over-ear headphones", "Electronics",
                                        new BigDecimal("149.99"))),
                        new AiCampaignRequest.CartItemContext(
                                2L, 1, new BigDecimal("39.99"),
                                new AiCampaignRequest.ProductContext(
                                        3L, "ELEC-003", "Portable Power Bank",
                                        "20000mAh fast-charging power bank", "Electronics",
                                        new BigDecimal("39.99")))),
                new BigDecimal("189.98"),
                "ABANDONED",
                "2026-08-15T14:30:00");
    }

    private String invokeBuildUserPrompt(AiCampaignRequest request) {
        try {
            var method = OpenAiCampaignService.class.getDeclaredMethod("buildUserPrompt", AiCampaignRequest.class);
            method.setAccessible(true);
            return (String) method.invoke(service, request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private AiCampaignResponse invokeParseResponse(String response, AiCampaignRequest request) {
        try {
            var method = OpenAiCampaignService.class.getDeclaredMethod("parseResponse", String.class, AiCampaignRequest.class);
            method.setAccessible(true);
            return (AiCampaignResponse) method.invoke(service, response, request);
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            if (cause instanceof Error) {
                throw (Error) cause;
            }
            throw new RuntimeException(cause);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}