package com.targetcart.ai.modules.campaign.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.targetcart.ai.modules.campaign.dto.CampaignDto;
import com.targetcart.ai.modules.campaign.service.CampaignService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping(path = "/api/campaigns", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(name = "Campaigns", description = "Read access to recovery campaigns")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping
    @Operation(summary = "List all campaigns",
            description = "Returns every recovery campaign. The database contains no campaign seed data, "
                    + "so this list is empty until the campaign workflow produces records.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Campaigns returned successfully"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    public List<CampaignDto> findAll() {
        return campaignService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a campaign by id",
            description = "Returns a single campaign. Returns 404 if no such campaign exists.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Campaign found"),
            @ApiResponse(responseCode = "400", description = "Invalid campaign id"),
            @ApiResponse(responseCode = "404", description = "Campaign does not exist")
    })
    public CampaignDto findById(@PathVariable @Min(value = 1, message = "id must be a positive number") Long id) {
        return campaignService.findById(id);
    }
}