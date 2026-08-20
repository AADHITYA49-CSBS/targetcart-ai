package com.targetcart.ai.modules.campaign.dto;

import java.time.LocalDateTime;

import com.targetcart.ai.modules.campaign.entity.Campaign;
import com.targetcart.ai.modules.campaign.entity.CampaignStatus;
import com.targetcart.ai.modules.campaign.entity.CampaignType;
import com.targetcart.ai.modules.cart.dto.CartSummaryDto;
import com.targetcart.ai.modules.user.dto.UserSummaryDto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Full representation of a recovery campaign as returned by the Campaigns API.
 * Embeds lightweight views of the target cart and user instead of the raw
 * JPA entities.
 */
@Schema(name = "Campaign", description = "A recovery campaign generated for an abandoned cart")
public record CampaignDto(
        @Schema(description = "Unique campaign identifier") Long id,
        @Schema(description = "The cart the campaign targets") CartSummaryDto cart,
        @Schema(description = "The customer the campaign is addressed to") UserSummaryDto user,
        @Schema(description = "Kind of campaign") CampaignType campaignType,
        @Schema(description = "Email subject line") String subject,
        @Schema(description = "Email body") String content,
        @Schema(description = "Lifecycle state of the campaign") CampaignStatus status,
        @Schema(description = "When the campaign content was generated") LocalDateTime generatedAt,
        @Schema(description = "When the campaign was sent") LocalDateTime sentAt,
        @Schema(description = "When the campaign was created") LocalDateTime createdAt,
        @Schema(description = "When the campaign was last updated") LocalDateTime updatedAt) {

    public static CampaignDto from(Campaign campaign) {
        return new CampaignDto(
                campaign.getId(),
                CartSummaryDto.from(campaign.getCart()),
                UserSummaryDto.from(campaign.getUser()),
                campaign.getCampaignType(),
                campaign.getSubject(),
                campaign.getContent(),
                campaign.getStatus(),
                campaign.getGeneratedAt(),
                campaign.getSentAt(),
                campaign.getCreatedAt(),
                campaign.getUpdatedAt());
    }
}