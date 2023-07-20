package com.gitlab.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductDtoTest extends AbstractDtoTest {

    private ProductDto getValidProductDto() {
        ProductDto productDto = new ProductDto();
        productDto.setName("product1");
        productDto.setStockCount(2);
        productDto.setDescription("product1");
        productDto.setIsAdult(true);
        productDto.setCode("123");
        productDto.setWeight(2L);
        productDto.setPrice(BigDecimal.ONE);
        return productDto;
    }

    @Test
    void test_valid_productDto() {

        assertTrue(validator.validate(getValidProductDto()).isEmpty());
    }

    @Test
    void test_invalid_name_length() {
        ProductDto productDto = getValidProductDto();
        productDto.setName("a");

        assertFalse(validator.validate(productDto).isEmpty());
        String expectedMessage = "Length of Product's name should be between 3 and 256 characters";
        assertEquals(expectedMessage, validator.validate(productDto).iterator().next().getMessage());
    }


    @Test
    void test_invalid_max_name_length() {
        ProductDto productDto = getValidProductDto();
        productDto.setName("a".repeat(257));

        assertFalse(validator.validate(productDto).isEmpty());
        String expectedMessage = "Length of Product's name should be between 3 and 256 characters";
        assertEquals(expectedMessage, validator.validate(productDto).iterator().next().getMessage());
    }


    @Test
    void test_null_name() {
        ProductDto productDto = getValidProductDto();
        productDto.setName(null);

        assertFalse(validator.validate(productDto).isEmpty());
        String expectedMessage = "Product's name should not be empty";

        assertEquals(expectedMessage, validator.validate(productDto).iterator().next().getMessage());
    }
    //////////////////////////////////////////////////////////////////////////////////


    @Test
    void test_invalid_stockCount_value() {
        ProductDto productDto = getValidProductDto();
        productDto.setStockCount(0);

        assertFalse(validator.validate(productDto).isEmpty());
        String expectedMessage = "Product's stockCount should be between 1 and 2147483333";
        assertEquals(expectedMessage, validator.validate(productDto).iterator().next().getMessage());
    }


    @Test
    void test_invalid_max_stockCount_value() {
        ProductDto productDto = getValidProductDto();
        productDto.setStockCount(Integer.MAX_VALUE);

        assertFalse(validator.validate(productDto).isEmpty());
        String expectedMessage = "Product's stockCount should be between 1 and 2147483333";
        assertEquals(expectedMessage, validator.validate(productDto).iterator().next().getMessage());
    }


    @Test
    void test_null_stockCount() {
        ProductDto productDto = getValidProductDto();
        productDto.setStockCount(null);

        assertFalse(validator.validate(productDto).isEmpty());
        String expectedMessage = "Product's stockCount should not be empty";
        assertEquals(expectedMessage, validator.validate(productDto).iterator().next().getMessage());
    }
    //////////////////////////////////////////////////////////////////////////////////

    @Test
    void test_invalid_description_length() {
        ProductDto productDto = getValidProductDto();
        productDto.setDescription("a");

        assertFalse(validator.validate(productDto).isEmpty());
        String expectedMessage = "Length of Product's description should be between 3 and 1000 characters";
        assertEquals(expectedMessage, validator.validate(productDto).iterator().next().getMessage());
    }


    @Test
    void test_invalid_max_description_length() {
        ProductDto productDto = getValidProductDto();
        productDto.setDescription("a".repeat(1001));

        assertFalse(validator.validate(productDto).isEmpty());
        String expectedMessage = "Length of Product's description should be between 3 and 1000 characters";
        assertEquals(expectedMessage, validator.validate(productDto).iterator().next().getMessage());
    }


    @Test
    void test_null_description() {
        ProductDto productDto = getValidProductDto();
        productDto.setDescription(null);

        assertFalse(validator.validate(productDto).isEmpty());
        String expectedMessage = "Product's description should not be empty";
        assertEquals(expectedMessage, validator.validate(productDto).iterator().next().getMessage());
    }
    //////////////////////////////////////////////////////////////////////////////////


    @Test
    void test_null_isAdult() {
        ProductDto productDto = getValidProductDto();
        productDto.setIsAdult(null);

        assertFalse(validator.validate(productDto).isEmpty());
        String expectedMessage = "Product's isAdult field should not be empty";
        assertEquals(expectedMessage, validator.validate(productDto).iterator().next().getMessage());
    }
    //////////////////////////////////////////////////////////////////////////////////

    @Test
    void test_invalid_code_length() {
        ProductDto productDto = getValidProductDto();
        productDto.setCode("a");

        assertFalse(validator.validate(productDto).isEmpty());
        String expectedMessage = "Length of Product's code should be between 2 and 256 characters";
        assertEquals(expectedMessage, validator.validate(productDto).iterator().next().getMessage());
    }


    @Test
    void test_invalid_max_code_length() {
        ProductDto productDto = getValidProductDto();
        productDto.setCode("a".repeat(257));

        assertFalse(validator.validate(productDto).isEmpty());
        String expectedMessage = "Length of Product's code should be between 2 and 256 characters";
        assertEquals(expectedMessage, validator.validate(productDto).iterator().next().getMessage());
    }


    @Test
    void test_null_code() {
        ProductDto productDto = getValidProductDto();
        productDto.setCode(null);

        assertFalse(validator.validate(productDto).isEmpty());
        String expectedMessage = "Product's code should not be empty";
        assertEquals(expectedMessage, validator.validate(productDto).iterator().next().getMessage());
    }
    //////////////////////////////////////////////////////////////////////////////////

    @Test
    void test_invalid_weight_value() {
        ProductDto productDto = getValidProductDto();
        productDto.setWeight(0L);

        assertFalse(validator.validate(productDto).isEmpty());
        String expectedMessage = "Product's weight should be between 1 and 2147483333";
        assertEquals(expectedMessage, validator.validate(productDto).iterator().next().getMessage());
    }


    @Test
    void test_invalid_max_weight_value() {
        ProductDto productDto = getValidProductDto();
        productDto.setWeight((long) Integer.MAX_VALUE);

        assertFalse(validator.validate(productDto).isEmpty());
        String expectedMessage = "Product's weight should be between 1 and 2147483333";
        assertEquals(expectedMessage, validator.validate(productDto).iterator().next().getMessage());
    }


    @Test
    void test_null_weight() {
        ProductDto productDto = getValidProductDto();
        productDto.setWeight(null);

        assertFalse(validator.validate(productDto).isEmpty());
        String expectedMessage = "Product's weight should not be empty";
        assertEquals(expectedMessage, validator.validate(productDto).iterator().next().getMessage());
    }
    //////////////////////////////////////////////////////////////////////////////////

    @Test
    void test_invalid_price_value() {
        ProductDto productDto = getValidProductDto();
        productDto.setPrice(BigDecimal.valueOf(0));

        assertFalse(validator.validate(productDto).isEmpty());
        String expectedMessage = "Product's price should be between 0.1 and 2147483333";
        assertEquals(expectedMessage, validator.validate(productDto).iterator().next().getMessage());
    }


    @Test
    void test_invalid_max_price_value() {
        ProductDto productDto = getValidProductDto();
        productDto.setPrice(BigDecimal.valueOf(Integer.MAX_VALUE));

        assertFalse(validator.validate(productDto).isEmpty());
        String expectedMessage = "Product's price should be between 0.1 and 2147483333";
        assertEquals(expectedMessage, validator.validate(productDto).iterator().next().getMessage());
    }


    @Test
    void test_null_price() {
        ProductDto productDto = getValidProductDto();
        productDto.setPrice(null);

        assertFalse(validator.validate(productDto).isEmpty());
        String expectedMessage = "Product's price should not be empty";
        assertEquals(expectedMessage, validator.validate(productDto).iterator().next().getMessage());
    }
}