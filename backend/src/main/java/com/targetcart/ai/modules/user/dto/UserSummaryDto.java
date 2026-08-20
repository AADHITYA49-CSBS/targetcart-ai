package com.targetcart.ai.modules.user.dto;

import com.targetcart.ai.modules.user.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Lightweight view of a user, embedded in cart and campaign responses so
 * related resources can be understood without exposing the full user entity.
 */
@Schema(name = "UserSummary", description = "Lightweight user reference embedded in other resources")
public record UserSummaryDto(
        @Schema(description = "Unique user identifier") Long id,
        @Schema(description = "Login email") String email,
        @Schema(description = "First name") String firstName,
        @Schema(description = "Last name") String lastName,
        @Schema(description = "Whether the user is a VIP customer") boolean vip) {

    public static UserSummaryDto from(User user) {
        return new UserSummaryDto(user.getId(), user.getEmail(), user.getFirstName(),
                user.getLastName(), user.isVip());
    }
}