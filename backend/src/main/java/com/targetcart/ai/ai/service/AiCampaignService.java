package com.targetcart.ai.ai.service;

import com.targetcart.ai.ai.dto.AiCampaignRequest;
import com.targetcart.ai.ai.dto.AiCampaignResponse;

/**
 * Service interface for AI-powered campaign generation.
 * Abstracts the underlying LLM provider for testability and future flexibility.
 */
public interface AiCampaignService {

    /**
     * Generates a personalized recovery campaign for an abandoned cart.
     *
     * @param request structured context including customer, cart items, and products
     * @return generated campaign with subject, content, and rationale
     * @throws AiGenerationException if the AI provider fails or returns invalid output
     */
    AiCampaignResponse generateCampaign(AiCampaignRequest request) throws AiGenerationException;
}