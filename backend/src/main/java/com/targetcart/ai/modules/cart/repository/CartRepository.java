package com.targetcart.ai.modules.cart.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.targetcart.ai.modules.cart.entity.Cart;
import com.targetcart.ai.modules.cart.entity.CartStatus;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByStatus(CartStatus status);

    List<Cart> findByUserId(Long userId);

    List<Cart> findByStatusAndAbandonedAtBefore(CartStatus status, LocalDateTime time);
}
