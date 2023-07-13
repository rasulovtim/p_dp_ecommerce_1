package com.gitlab.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductDtoTest extends AbstractDtoTest {

    @Test
    void test_valid_product_name_length() {
        ProductDto productDto = new ProductDto();
        productDto.setName("product1");
        productDto.setStockCount(1);
        productDto.setDescription("product1");
        productDto.setIsAdult(true);
        productDto.setCode("1");
        productDto.setWeight(1L);
        productDto.setPrice(BigDecimal.ONE);

        assertTrue(validator.validate(productDto).isEmpty());
    }


    @Test
    void test_default_product_name_invalid_length() {
        ProductDto productDto = new ProductDto();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < 128; i++) sb.append(i);
        productDto.setName(sb.toString());
        productDto.setStockCount(1);
        productDto.setDescription("product1");
        productDto.setIsAdult(true);
        productDto.setCode("1");
        productDto.setWeight(1L);
        productDto.setPrice(BigDecimal.ONE);
        String expectedMessage = "Length of Product's name should be between 5 and 60 characters";
        String actualMessage = validator.validate(productDto).iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    void test_default_message_null_product_name() {
        ProductDto productDto = new ProductDto();
        productDto.setName(null);
        productDto.setStockCount(1);
        productDto.setDescription("product1");
        productDto.setIsAdult(true);
        productDto.setCode("1");
        productDto.setWeight(1L);
        productDto.setPrice(BigDecimal.ONE);
        String expectedMessage = "Product's name should have at least three characters";
        String actualMessage = validator.validate(productDto).iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    void test_default_message_null_product_stockCount() {
        ProductDto productDto = new ProductDto();
        productDto.setName("product1");
        productDto.setStockCount(null);
        productDto.setDescription("product1");
        productDto.setIsAdult(true);
        productDto.setCode("1");
        productDto.setWeight(1L);
        productDto.setPrice(BigDecimal.ONE);
        String expectedMessage = "Product's stockCount should not be empty";
        String actualMessage = validator.validate(productDto).iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    void test_default_message_null_product_description() {
        ProductDto productDto = new ProductDto();
        productDto.setName("product1");
        productDto.setStockCount(1);
        productDto.setDescription(null);
        productDto.setIsAdult(true);
        productDto.setCode("1");
        productDto.setWeight(1L);
        productDto.setPrice(BigDecimal.ONE);
        String expectedMessage = "Product's description should not be empty";
        String actualMessage = validator.validate(productDto).iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    void test_default_message_null_product_isAdult() {
        ProductDto productDto = new ProductDto();
        productDto.setName("product1");
        productDto.setStockCount(1);
        productDto.setDescription("product1");
        productDto.setIsAdult(null);
        productDto.setCode("1");
        productDto.setWeight(1L);
        productDto.setPrice(BigDecimal.ONE);
        String expectedMessage = "Product's isAdult field should not be empty";
        String actualMessage = validator.validate(productDto).iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    void test_default_message_null_product_code() {
        ProductDto productDto = new ProductDto();
        productDto.setName("product1");
        productDto.setStockCount(1);
        productDto.setDescription("product1");
        productDto.setIsAdult(true);
        productDto.setCode(null);
        productDto.setWeight(1L);
        productDto.setPrice(BigDecimal.ONE);
        String expectedMessage = "Product's code should not be empty";
        String actualMessage = validator.validate(productDto).iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    void test_default_message_null_product_weight() {
        ProductDto productDto = new ProductDto();
        productDto.setName("product1");
        productDto.setStockCount(1);
        productDto.setDescription("product1");
        productDto.setIsAdult(true);
        productDto.setCode("1");
        productDto.setWeight(null);
        productDto.setPrice(BigDecimal.ONE);
        String expectedMessage = "Product's weight should not be empty";
        String actualMessage = validator.validate(productDto).iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    void test_default_message_null_product_price() {
        ProductDto productDto = new ProductDto();
        productDto.setName("product1");
        productDto.setStockCount(1);
        productDto.setDescription("product1");
        productDto.setIsAdult(true);
        productDto.setCode("1");
        productDto.setWeight(1L);
        productDto.setPrice(null);
        String expectedMessage = "Product's price should not be empty";
        String actualMessage = validator.validate(productDto).iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
    }


}