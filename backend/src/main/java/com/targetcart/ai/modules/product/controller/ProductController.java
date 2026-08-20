package com.targetcart.ai.modules.product.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.targetcart.ai.modules.product.dto.ProductDto;
import com.targetcart.ai.modules.product.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping(path = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(name = "Products", description = "Read access to the product catalog")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "List all products",
            description = "Returns every product in the catalog ordered by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products returned successfully"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    public List<ProductDto> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product by id",
            description = "Returns a single product. Returns 404 if no such product exists.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "400", description = "Invalid product id"),
            @ApiResponse(responseCode = "404", description = "Product does not exist")
    })
    public ProductDto findById(@PathVariable @Min(value = 1, message = "id must be a positive number") Long id) {
        return productService.findById(id);
    }
}