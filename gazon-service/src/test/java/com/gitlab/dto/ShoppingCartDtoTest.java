package com.gitlab.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartDtoTest extends AbstractDtoTest {

    @Test
    void test_valid_properties() {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setUserId(1L);
        shoppingCartDto.setSum(BigDecimal.valueOf(100));
        shoppingCartDto.setTotalWeight(500L);

        assertTrue(validator.validate(shoppingCartDto).isEmpty());
    }

    @Test
    void test_null_userId() {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setUserId(null);
        shoppingCartDto.setSum(BigDecimal.valueOf(100));
        shoppingCartDto.setTotalWeight(500L);

        assertFalse(validator.validate(shoppingCartDto).isEmpty());
    }

    @Test
    void test_null_sum() {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setUserId(1L);
        shoppingCartDto.setSum(null);
        shoppingCartDto.setTotalWeight(500L);

        assertFalse(validator.validate(shoppingCartDto).isEmpty());
    }

    @Test
    void test_null_totalWeight() {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setUserId(1L);
        shoppingCartDto.setSum(BigDecimal.valueOf(100));
        shoppingCartDto.setTotalWeight(null);

        assertFalse(validator.validate(shoppingCartDto).isEmpty());
    }

    @Test
    void test_default_message_null_userId() {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setUserId(null);
        shoppingCartDto.setSum(BigDecimal.valueOf(100));
        shoppingCartDto.setTotalWeight(500L);
        String expectedMessage = "User ID should not be null.";
        String actualMessage = validator.validate(shoppingCartDto).iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    // Add more tests for other properties and validation constraints
}
