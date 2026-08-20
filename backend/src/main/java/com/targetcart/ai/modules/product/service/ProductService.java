package com.targetcart.ai.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.targetcart.ai.common.error.ResourceNotFoundException;
import com.targetcart.ai.modules.product.dto.ProductDto;
import com.targetcart.ai.modules.product.repository.ProductRepository;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(ProductDto::from)
                .toList();
    }

    public ProductDto findById(Long id) {
        return productRepository.findById(id)
                .map(ProductDto::from)
                .orElseThrow(() -> ResourceNotFoundException.of("Product", id));
    }
}