package com.targetcart.ai.modules.cart.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.targetcart.ai.modules.cart.entity.CartItem;
import com.targetcart.ai.modules.product.dto.ProductDto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * A single product line inside a cart. Embeds the product details so the
 * response is self-contained and needs no follow-up lookup.
 */
@Schema(name = "CartItem", description = "A product line inside a cart")
public record CartItemDto(
        @Schema(description = "Unique cart item identifier") Long id,
        @Schema(description = "Number of units in the cart") int quantity,
        @Schema(description = "Unit price at the time the item was added") BigDecimal unitPrice,
        @Schema(description = "The product this line refers to") ProductDto product) {

    public static CartItemDto from(CartItem item) {
        return new CartItemDto(
                item.getId(),
                item.getQuantity(),
                item.getUnitPrice(),
                ProductDto.from(item.getProduct()));
    }
}