package com.targetcart.ai.modules.product.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.targetcart.ai.modules.product.entity.Product;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Full representation of a product returned by the Products API.
 * Deliberately exposes only scalar catalog fields and no entity relationships.
 */
@Schema(name = "Product", description = "A product in the catalog")
public record ProductDto(
        @Schema(description = "Unique product identifier") Long id,
        @Schema(description = "Stock keeping unit, unique per product") String sku,
        @Schema(description = "Display name") String name,
        @Schema(description = "Long-form description") String description,
        @Schema(description = "Catalog category") String category,
        @Schema(description = "Current unit price") BigDecimal price,
        @Schema(description = "Units available in stock") int stockQuantity,
        @Schema(description = "When the product was created") LocalDateTime createdAt,
        @Schema(description = "When the product was last updated") LocalDateTime updatedAt) {

    public static ProductDto from(Product product) {
        return new ProductDto(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getCreatedAt(),
                product.getUpdatedAt());
    }
}