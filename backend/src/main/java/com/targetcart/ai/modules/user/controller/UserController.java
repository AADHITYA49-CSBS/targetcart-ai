package com.targetcart.ai.modules.user.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.targetcart.ai.modules.user.dto.UserDto;
import com.targetcart.ai.modules.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(name = "Users", description = "Read access to the customer directory")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "List all users",
            description = "Returns every customer in the platform ordered by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users returned successfully"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by id",
            description = "Returns a single customer. Returns 404 if no such user exists.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "400", description = "Invalid user id"),
            @ApiResponse(responseCode = "404", description = "User does not exist")
    })
    public UserDto findById(@PathVariable @Min(value = 1, message = "id must be a positive number") Long id) {
        return userService.findById(id);
    }
}