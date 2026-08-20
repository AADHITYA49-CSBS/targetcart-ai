package com.targetcart.ai.modules.cart.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.targetcart.ai.common.error.ResourceNotFoundException;
import com.targetcart.ai.modules.cart.dto.CartDto;
import com.targetcart.ai.modules.cart.entity.Cart;
import com.targetcart.ai.modules.cart.entity.CartStatus;
import com.targetcart.ai.modules.cart.repository.CartRepository;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<CartDto> findAll() {
        return cartRepository.findAll().stream()
                .map(CartDto::from)
                .toList();
    }

    public CartDto findById(Long id) {
        return cartRepository.findById(id)
                .map(CartDto::from)
                .orElseThrow(() -> ResourceNotFoundException.of("Cart", id));
    }

    /**
     * Returns carts whose persisted status is ABANDONED.
     * The status comes straight from the carts.status column; nothing is
     * derived or manufactured in memory.
     */
    public List<CartDto> findAbandoned() {
        return cartRepository.findByStatusOrderByAbandonedAtDesc(CartStatus.ABANDONED).stream()
                .map(CartDto::from)
                .toList();
    }
}