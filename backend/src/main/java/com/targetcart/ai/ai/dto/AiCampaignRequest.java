package com.targetcart.ai.ai.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Request DTO for AI campaign generation.
 * Contains structured cart, customer, and product context for the LLM.
 */
@Schema(name = "AiCampaignRequest", description = "Structured context for AI-powered campaign generation")
public record AiCampaignRequest(
        @Schema(description = "Cart identifier") Long cartId,
        @Schema(description = "Customer information") CustomerContext customer,
        @Schema(description = "Cart items with product details") List<CartItemContext> items,
        @Schema(description = "Total cart amount") BigDecimal totalAmount,
        @Schema(description = "Cart status (e.g., ABANDONED)") String cartStatus,
        @Schema(description = "Timestamp when cart was abandoned") String abandonedAt) {

    @Schema(name = "CustomerContext", description = "Lightweight customer profile for personalization")
    public record CustomerContext(
            @Schema(description = "Customer identifier") Long id,
            @Schema(description = "Customer email") String email,
            @Schema(description = "First name") String firstName,
            @Schema(description = "Last name") String lastName,
            @Schema(description = "VIP status") boolean vip,
            @Schema(description = "Total lifetime orders") int totalOrders,
            @Schema(description = "Total lifetime spend") BigDecimal totalSpend) {
    }

    @Schema(name = "CartItemContext", description = "Cart item with embedded product details")
    public record CartItemContext(
            @Schema(description = "Cart item identifier") Long id,
            @Schema(description = "Quantity in cart") int quantity,
            @Schema(description = "Unit price at time of addition") BigDecimal unitPrice,
            @Schema(description = "Product details") ProductContext product) {
    }

    @Schema(name = "ProductContext", description = "Product details for campaign context")
    public record ProductContext(
            @Schema(description = "Product identifier") Long id,
            @Schema(description = "Product SKU") String sku,
            @Schema(description = "Product name") String name,
            @Schema(description = "Product description") String description,
            @Schema(description = "Product category") String category,
            @Schema(description = "Current product price") BigDecimal price) {
    }
}