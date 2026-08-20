package com.targetcart.ai.modules.execution.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.targetcart.ai.modules.execution.dto.ExecutionLogDto;
import com.targetcart.ai.modules.execution.service.ExecutionLogService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping(path = "/api/execution-logs", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(name = "Execution Logs", description = "Read access to campaign execution logs")
public class ExecutionLogController {

    private final ExecutionLogService executionLogService;

    public ExecutionLogController(ExecutionLogService executionLogService) {
        this.executionLogService = executionLogService;
    }

    @GetMapping
    @Operation(summary = "List all execution logs",
            description = "Returns every execution log. The database contains no execution seed data, "
                    + "so this list is empty until the campaign workflow produces records.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Execution logs returned successfully"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    public List<ExecutionLogDto> findAll() {
        return executionLogService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an execution log by id",
            description = "Returns a single execution log. Returns 404 if no such log exists.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Execution log found"),
            @ApiResponse(responseCode = "400", description = "Invalid execution log id"),
            @ApiResponse(responseCode = "404", description = "Execution log does not exist")
    })
    public ExecutionLogDto findById(@PathVariable @Min(value = 1, message = "id must be a positive number") Long id) {
        return executionLogService.findById(id);
    }
}