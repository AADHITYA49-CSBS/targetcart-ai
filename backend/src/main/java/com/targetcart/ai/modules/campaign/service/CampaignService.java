package com.targetcart.ai.modules.campaign.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.targetcart.ai.common.error.ResourceNotFoundException;
import com.targetcart.ai.modules.campaign.dto.CampaignDto;
import com.targetcart.ai.modules.campaign.repository.CampaignRepository;

@Service
@Transactional(readOnly = true)
public class CampaignService {

    private final CampaignRepository campaignRepository;

    public CampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    public List<CampaignDto> findAll() {
        return campaignRepository.findAll().stream()
                .map(CampaignDto::from)
                .toList();
    }

    public CampaignDto findById(Long id) {
        return campaignRepository.findById(id)
                .map(CampaignDto::from)
                .orElseThrow(() -> ResourceNotFoundException.of("Campaign", id));
    }
}