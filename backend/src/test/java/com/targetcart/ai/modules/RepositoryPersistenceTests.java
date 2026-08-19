package com.targetcart.ai.modules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.targetcart.ai.modules.campaign.repository.CampaignRepository;
import com.targetcart.ai.modules.cart.entity.Cart;
import com.targetcart.ai.modules.cart.entity.CartItem;
import com.targetcart.ai.modules.cart.repository.CartItemRepository;
import com.targetcart.ai.modules.cart.repository.CartRepository;
import com.targetcart.ai.modules.execution.repository.ExecutionLogRepository;
import com.targetcart.ai.modules.product.repository.ProductRepository;
import com.targetcart.ai.modules.user.entity.User;
import com.targetcart.ai.modules.user.repository.UserRepository;
import com.targetcart.ai.test.AbstractMySqlTest;

@Transactional
class RepositoryPersistenceTests extends AbstractMySqlTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private ExecutionLogRepository executionLogRepository;

    @Test
    void allRepositoriesAreWired() {
        assertNotNull(userRepository);
        assertNotNull(productRepository);
        assertNotNull(cartRepository);
        assertNotNull(cartItemRepository);
        assertNotNull(campaignRepository);
        assertNotNull(executionLogRepository);
    }

    @Test
    void seedRowCountsMatchDevelopmentDatabase() {
        assertEquals(8L, userRepository.count());
        assertEquals(12L, productRepository.count());
        assertEquals(12L, cartRepository.count());
        assertEquals(18L, cartItemRepository.count());
        assertEquals(0L, campaignRepository.count());
        assertEquals(0L, executionLogRepository.count());
    }

    @Test
    void findUserByEmail() {
        Optional<User> user = userRepository.findByEmail("alice.johnson@example.com");
        assertTrue(user.isPresent());
        assertTrue(user.get().isVip());
    }

    @Test
    void cartBelongsToUserAndHasItems() {
        Cart cart = cartRepository.findById(1L).orElseThrow();
        assertNotNull(cart.getUser());
        assertFalse(cart.getItems().isEmpty());
        assertEquals(2L, cartItemRepository.countByCartId(1L));
    }

    @Test
    void cartTotalsEqualSumOfCartItems() {
        for (Cart cart : cartRepository.findAll()) {
            BigDecimal expected = cartItemRepository.findByCartId(cart.getId()).stream()
                    .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            assertEquals(expected, cart.getTotalAmount(), "Cart " + cart.getId());
        }
    }

    @Test
    void cartItemReferencesProduct() {
        CartItem item = cartItemRepository.findByCartId(1L).get(0);
        assertNotNull(item.getProduct());
        assertNotNull(item.getProduct().getSku());
    }

    @Test
    void saveNewUserAndRollBack() {
        User user = new User("fresh." + System.nanoTime() + "@example.com",
                "Fresh", "User", false, 0, BigDecimal.ZERO);
        userRepository.saveAndFlush(user);
        assertNotNull(user.getId());
        assertTrue(userRepository.existsByEmail(user.getEmail()));
    }
}
