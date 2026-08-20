package com.targetcart.ai.modules;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import com.targetcart.ai.test.AbstractMySqlTest;

/**
 * Integration tests for the REST API backed by the real MySQL development
 * database. Proves the API works end to end and never serializes the JPA
 * object graph.
 */
@AutoConfigureMockMvc
class RestApiIntegrationTests extends AbstractMySqlTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getUsersReturnsSeededUsers() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)))
                .andExpect(jsonPath("$[0].email").value("alice.johnson@example.com"))
                .andExpect(jsonPath("$[0].totalOrders").value(12))
                .andExpect(jsonPath("$[0].vip").value(true));
    }

    @Test
    void getUsersDoesNotSerializeCarts() throws Exception {
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("alice.johnson@example.com"))
                .andExpect(jsonPath("$.carts").doesNotExist())
                .andExpect(jsonPath("$.cartItems").doesNotExist());
    }

    @Test
    void getProductsReturnsSeededProducts() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(12)))
                .andExpect(jsonPath("$[0].sku").value("ELEC-001"))
                .andExpect(jsonPath("$[0].price").value(149.99));
    }

    @Test
    void getProductById() throws Exception {
        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.category").value("Electronics"))
                .andExpect(jsonPath("$.cartItems").doesNotExist());
    }

    @Test
    void getCartsReturnsCartsWithUserItemsAndProducts() throws Exception {
        mockMvc.perform(get("/api/carts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(12)))
                .andExpect(jsonPath("$[0].user.email").isNotEmpty())
                .andExpect(jsonPath("$[0].status").value("ACTIVE"))
                .andExpect(jsonPath("$[0].items", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$[0].items[0].product.sku").isNotEmpty())
                .andExpect(jsonPath("$[0].totalAmount").value(162.98))
                .andExpect(jsonPath("$[0].user.carts").doesNotExist())
                .andExpect(jsonPath("$[0].items[0].product.cartItems").doesNotExist())
                .andExpect(jsonPath("$[0].items[0].cart").doesNotExist());
    }

    @Test
    void getCartById() throws Exception {
        mockMvc.perform(get("/api/carts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.items", hasSize(2)))
                .andExpect(jsonPath("$.items[0].product.name").value("Wireless Bluetooth Headphones"));
    }

    @Test
    void getAbandonedCartsReturnsRealPersistedData() throws Exception {
        mockMvc.perform(get("/api/carts/abandoned"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[*].status", everyItem(is("ABANDONED"))))
                .andExpect(jsonPath("$[*].abandonedAt", everyItem(not(is((Object) null)))))
                .andExpect(jsonPath("$[*].id", hasItem(4)))
                .andExpect(jsonPath("$[*].id", hasItem(7)));
    }

    @Test
    void nonexistentUserReturns404() throws Exception {
        mockMvc.perform(get("/api/users/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.path").value("/api/users/999999"))
                .andExpect(jsonPath("$.message").value("User not found with id 999999"))
                .andExpect(jsonPath("$.stackTrace").doesNotExist());
    }

    @Test
    void nonexistentCartReturns404() throws Exception {
        mockMvc.perform(get("/api/carts/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void invalidPathVariableReturns400() throws Exception {
        mockMvc.perform(get("/api/users/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"));
    }

    @Test
    void campaignsAreEmptyUntilWorkflowProducesRecords() throws Exception {
        mockMvc.perform(get("/api/campaigns"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void nonexistentCampaignReturns404() throws Exception {
        mockMvc.perform(get("/api/campaigns/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void executionLogsAreEmptyUntilWorkflowProducesRecords() throws Exception {
        mockMvc.perform(get("/api/execution-logs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void nonexistentExecutionLogReturns404() throws Exception {
        mockMvc.perform(get("/api/execution-logs/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void unknownPathReturns404() throws Exception {
        mockMvc.perform(get("/api/nope"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }
}