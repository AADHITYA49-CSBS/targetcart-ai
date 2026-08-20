package com.targetcart.ai.modules.cart.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.targetcart.ai.modules.cart.entity.Cart;
import com.targetcart.ai.modules.cart.entity.CartStatus;
import com.targetcart.ai.modules.user.dto.UserSummaryDto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Complete view of a cart as returned by the Carts API.
 *
 * <p>Includes the owning user, every cart item (with embedded product
 * details), the persisted total amount and the cart status. The JPA entity is
 * never serialized directly, so recursive relationships are impossible.
 */
@Schema(name = "Cart", description = "A shopping cart with its owner, items and products")
public record CartDto(
        @Schema(description = "Unique cart identifier") Long id,
        @Schema(description = "The customer who owns the cart") UserSummaryDto user,
        @Schema(description = "Lifecycle state of the cart") CartStatus status,
        @Schema(description = "Total value of the cart as persisted in the database") BigDecimal totalAmount,
        @Schema(description = "When the cart was abandoned, if it was") LocalDateTime abandonedAt,
        @Schema(description = "When the cart was recovered, if it was") LocalDateTime recoveredAt,
        @Schema(description = "When the cart was created") LocalDateTime createdAt,
        @Schema(description = "When the cart was last updated") LocalDateTime updatedAt,
        @Schema(description = "Product lines inside the cart") List<CartItemDto> items) {

    public static CartDto from(Cart cart) {
        return new CartDto(
                cart.getId(),
                UserSummaryDto.from(cart.getUser()),
                cart.getStatus(),
                cart.getTotalAmount(),
                cart.getAbandonedAt(),
                cart.getRecoveredAt(),
                cart.getCreatedAt(),
                cart.getUpdatedAt(),
                cart.getItems().stream()
                        .map(CartItemDto::from)
                        .toList());
    }
}