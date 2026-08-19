package com.targetcart.ai.modules.cart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.targetcart.ai.modules.cart.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCartId(Long cartId);

    long countByCartId(Long cartId);
}
