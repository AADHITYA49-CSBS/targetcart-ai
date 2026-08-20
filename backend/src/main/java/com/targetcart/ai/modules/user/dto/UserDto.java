package com.targetcart.ai.modules.user.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.targetcart.ai.modules.user.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Full representation of a user returned by the Users API.
 * Backed by the {@link User} JPA entity but intentionally free of entity
 * relationships so the API never serializes the object graph.
 */
@Schema(name = "User", description = "Customer on the e-commerce platform")
public record UserDto(
        @Schema(description = "Unique user identifier") Long id,
        @Schema(description = "Login email, unique per user") String email,
        @Schema(description = "First name") String firstName,
        @Schema(description = "Last name") String lastName,
        @Schema(description = "Whether the user is a VIP customer") boolean vip,
        @Schema(description = "Total number of completed orders") int totalOrders,
        @Schema(description = "Lifetime value of completed orders") BigDecimal totalSpend,
        @Schema(description = "When the user was created") LocalDateTime createdAt,
        @Schema(description = "When the user was last updated") LocalDateTime updatedAt) {

    public static UserDto from(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.isVip(),
                user.getTotalOrders(),
                user.getTotalSpend(),
                user.getCreatedAt(),
                user.getUpdatedAt());
    }
}