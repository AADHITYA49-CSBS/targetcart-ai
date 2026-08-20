package com.targetcart.ai.modules.cart.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.targetcart.ai.modules.cart.entity.Cart;
import com.targetcart.ai.modules.cart.entity.CartStatus;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Lightweight view of a cart embedded in campaign responses.
 */
@Schema(name = "CartSummary", description = "Lightweight cart reference embedded in other resources")
public record CartSummaryDto(
        @Schema(description = "Unique cart identifier") Long id,
        @Schema(description = "Lifecycle state of the cart") CartStatus status,
        @Schema(description = "Total value of the cart as persisted in the database") BigDecimal totalAmount,
        @Schema(description = "When the cart was abandoned, if it was") LocalDateTime abandonedAt) {

    public static CartSummaryDto from(Cart cart) {
        return new CartSummaryDto(cart.getId(), cart.getStatus(), cart.getTotalAmount(), cart.getAbandonedAt());
    }
}