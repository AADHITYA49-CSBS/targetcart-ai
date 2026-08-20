package com.targetcart.ai.ai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

import com.targetcart.ai.ai.dto.AiCampaignRequest;
import com.targetcart.ai.ai.dto.AiCampaignResponse;

/**
 * OpenAI implementation of {@link AiCampaignService} using Spring AI's ChatClient.
 *
 * <p>Uses a structured system prompt to generate personalized abandoned-cart
 * recovery campaigns. The prompt includes customer profile, cart contents,
 * and product details to produce relevant, personalized output.
 */
@Service
public class OpenAiCampaignService implements AiCampaignService {

    private static final Logger log = LoggerFactory.getLogger(OpenAiCampaignService.class);

    private final ChatClient chatClient;
    private final String systemPrompt;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OpenAiCampaignService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
        this.systemPrompt = loadSystemPrompt();
    }

    @Override
    public AiCampaignResponse generateCampaign(AiCampaignRequest request) throws AiGenerationException {
        try {
            String userPrompt = buildUserPrompt(request);
            log.debug("Generating campaign for cart {} with prompt length {}", request.cartId(), userPrompt.length());

            var response = chatClient.prompt()
                    .system(systemPrompt)
                    .user(userPrompt)
                    .options(OpenAiChatOptions.builder()
                            .model("gpt-4o-mini")
                            .temperature(0.7)
                            .maxTokens(1000)
                            .build())
                    .call()
                    .entity(String.class);

            return parseResponse(response, request);
        } catch (Exception e) {
            log.error("AI campaign generation failed for cart {}", request.cartId(), e);
            throw new AiGenerationException("Failed to generate campaign: " + e.getMessage(), e);
        }
    }

    private String loadSystemPrompt() {
        return """
                You are an expert e-commerce copywriter specializing in abandoned cart recovery emails.
                Generate a personalized, compelling recovery email based on the provided customer and cart context.

                Output format (JSON only, no markdown, no extra text):
                {
                  "subject": "string",
                  "content": "string",
                  "personalizationRationale": "string"
                }

                Guidelines:
                - Subject line: concise, urgent but not spammy, personalized where possible
                - Content: friendly, helpful tone; reference specific items; include clear call-to-action
                - Personalization rationale: 1-2 sentences explaining key personalization decisions
                - Never invent discounts or promotions not in the context
                - Keep content under 300 words
                - Use customer's first name naturally
                - Reference specific product names and categories from the cart
                """;
    }

    private String buildUserPrompt(AiCampaignRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("Customer Context:\n");
        sb.append("  Name: ").append(request.customer().firstName()).append(" ").append(request.customer().lastName()).append("\n");
        sb.append("  Email: ").append(request.customer().email()).append("\n");
        sb.append("  VIP: ").append(request.customer().vip()).append("\n");
        sb.append("  Total Orders: ").append(request.customer().totalOrders()).append("\n");
        sb.append("  Lifetime Spend: $").append(request.customer().totalSpend()).append("\n\n");

        sb.append("Cart Context:\n");
        sb.append("  Cart ID: ").append(request.cartId()).append("\n");
        sb.append("  Status: ").append(request.cartStatus()).append("\n");
        sb.append("  Abandoned At: ").append(request.abandonedAt()).append("\n");
        sb.append("  Total Amount: $").append(request.totalAmount()).append("\n\n");

        sb.append("Cart Items:\n");
        for (AiCampaignRequest.CartItemContext item : request.items()) {
            AiCampaignRequest.ProductContext product = item.product();
            sb.append("  - ").append(product.name()).append(" (").append(product.category()).append(")")
              .append(" x").append(item.quantity())
              .append(" @ $").append(item.unitPrice()).append("\n");
            if (product.description() != null && !product.description().isBlank()) {
                sb.append("    Description: ").append(product.description()).append("\n");
            }
        }

        return sb.toString();
    }

    private AiCampaignResponse parseResponse(String response, AiCampaignRequest request) throws AiGenerationException {
        try {
            JsonNode root = objectMapper.readTree(response);

            String subject = root.path("subject").asText(null);
            String content = root.path("content").asText(null);
            String rationale = root.path("personalizationRationale").asText(null);

            if (subject == null || subject.isBlank() || content == null || content.isBlank()) {
                throw new AiGenerationException("Invalid AI response: missing required fields");
            }

            return new AiCampaignResponse(
                    subject.trim(),
                    content.trim(),
                    rationale != null && !rationale.isBlank() ? rationale.trim() : "Generated based on cart context and customer profile",
                    "gpt-4o-mini",
                    Instant.now(),
                    null, // inputTokens - would need ChatResponse metadata
                    null  // outputTokens
            );
        } catch (Exception e) {
            throw new AiGenerationException("Failed to parse AI response: " + e.getMessage(), e);
        }
    }
}