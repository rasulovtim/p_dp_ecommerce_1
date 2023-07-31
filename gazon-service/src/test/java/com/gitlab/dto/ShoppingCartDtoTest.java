package com.gitlab.dto;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShoppingCartDtoTest extends AbstractDtoTest {

    @Test
    void test_valid_shopping_cart() {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setUserId(1L);
        shoppingCartDto.setSelectedProducts(Set.of("Product1", "Product2"));
        shoppingCartDto.setSum(BigDecimal.valueOf(100));
        shoppingCartDto.setTotalWeight(500L);

        assertTrue(validator.validate(shoppingCartDto).isEmpty());
    }

    @Test
    void test_invalid_user_id() {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setUserId(null);
        shoppingCartDto.setSelectedProducts(Set.of("Product1", "Product2"));
        shoppingCartDto.setSum(BigDecimal.valueOf(100));
        shoppingCartDto.setTotalWeight(500L);

        assertFalse(validator.validate(shoppingCartDto).isEmpty());
    }

    @Test
    void test_invalid_selected_products() {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setUserId(1L);
        shoppingCartDto.setSelectedProducts(new HashSet<>()); // Empty set, should be at least one product
        shoppingCartDto.setSum(BigDecimal.valueOf(100));
        shoppingCartDto.setTotalWeight(500L);

        assertFalse(validator.validate(shoppingCartDto).isEmpty());
    }

    @Test
    void test_default_message_user_id_null() {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setUserId(null);
        shoppingCartDto.setSelectedProducts(Set.of("Product1", "Product2"));
        shoppingCartDto.setSum(BigDecimal.valueOf(100));
        shoppingCartDto.setTotalWeight(500L);

        String expectedMessage = "User ID should not be null.";
        String actualMessage = validator.validate(shoppingCartDto)
                .iterator()
                .next()
                .getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void test_default_message_selected_products_empty() {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setUserId(1L);
        shoppingCartDto.setSelectedProducts(new HashSet<>()); // Empty set, should be at least one product
        shoppingCartDto.setSum(BigDecimal.valueOf(100));
        shoppingCartDto.setTotalWeight(500L);

        String expectedMessage = "At least one selected product should be present.";
        String actualMessage = validator.validate(shoppingCartDto)
                .iterator()
                .next()
                .getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
