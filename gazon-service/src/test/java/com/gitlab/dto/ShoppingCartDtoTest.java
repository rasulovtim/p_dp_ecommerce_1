package com.gitlab.dto;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
public class ShoppingCartDtoTest extends AbstractDtoTest {

    @Test
    void test_valid_properties() {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setUserId(1L);
        shoppingCartDto.setSum(BigDecimal.valueOf(100));
        shoppingCartDto.setTotalWeight(500L);

        Set<ConstraintViolation<ShoppingCartDto>> violations = validator.validate(shoppingCartDto);

        assertEquals(0, violations.size(), "There should be no validation violations");
    }

    @Test
    void test_default_message_null_userId() {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setUserId(null);
        shoppingCartDto.setSum(BigDecimal.valueOf(100));
        shoppingCartDto.setTotalWeight(500L);
        String expectedMessage = "ShoppingCart's userId should not be empty";
        String actualMessage = validator.validate(shoppingCartDto).iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

}
