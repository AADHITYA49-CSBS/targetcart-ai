package com.targetcart.ai.modules.cart.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.targetcart.ai.modules.cart.dto.CartDto;
import com.targetcart.ai.modules.cart.service.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping(path = "/api/carts", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(name = "Carts", description = "Read access to shopping carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    @Operation(summary = "List all carts",
            description = "Returns every cart with its owner, items and products.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Carts returned successfully"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    public List<CartDto> findAll() {
        return cartService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a cart by id",
            description = "Returns a single cart with its owner, items and products. Returns 404 if no such cart exists.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart found"),
            @ApiResponse(responseCode = "400", description = "Invalid cart id"),
            @ApiResponse(responseCode = "404", description = "Cart does not exist")
    })
    public CartDto findById(@PathVariable @Min(value = 1, message = "id must be a positive number") Long id) {
        return cartService.findById(id);
    }

    @GetMapping("/abandoned")
    @Operation(summary = "List abandoned carts",
            description = "Returns carts whose persisted status is ABANDONED, newest abandoned first. "
                    + "The status comes from the carts.status column in the database.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Abandoned carts returned successfully"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    public List<CartDto> findAbandoned() {
        return cartService.findAbandoned();
    }
}