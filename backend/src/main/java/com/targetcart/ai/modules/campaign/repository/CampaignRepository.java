package com.targetcart.ai.modules.campaign.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.targetcart.ai.modules.campaign.entity.Campaign;
import com.targetcart.ai.modules.campaign.entity.CampaignStatus;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    List<Campaign> findByStatus(CampaignStatus status);

    List<Campaign> findByUserId(Long userId);

    List<Campaign> findByCartId(Long cartId);
}
