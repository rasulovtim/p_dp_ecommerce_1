package com.gitlab.dto;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ProductImageDtoTest extends AbstractDtoTest {

    private ProductImageDto getValidImageDto() {
        var productImageDto = new ProductImageDto();
        productImageDto.setProductId(1L);
        productImageDto.setName("image1");
        productImageDto.setData(new byte[1]);
        return productImageDto;
    }

    @Test
    void test_valid_imageDto() {

        assertTrue(validator.validate(getValidImageDto()).isEmpty());
    }

    @Test
    void test_invalid_max_name_length() {
        ProductImageDto imageDto = getValidImageDto();
        imageDto.setName("a".repeat(257));

        assertFalse(validator.validate(imageDto).isEmpty());
        String expectedMessage = "Length of ProductImage's name should be between 1 and 256 characters";
        assertEquals(expectedMessage, validator.validate(imageDto).iterator().next().getMessage());
    }


    @Test
    void test_null_name() {
        ProductImageDto imageDto = getValidImageDto();
        imageDto.setName(null);

        assertFalse(validator.validate(imageDto).isEmpty());
        String expectedMessage = "ProductImage's name should not be empty";

        assertEquals(expectedMessage, validator.validate(imageDto).iterator().next().getMessage());
    }
    //////////////////////////////////////////////////////////////////////////////////


    @Test
    void test_null_productId() {
        ProductImageDto imageDto = getValidImageDto();
        imageDto.setProductId(null);

        assertFalse(validator.validate(imageDto).isEmpty());
        String expectedMessage = "ProductImage's productId should not be empty";
        assertEquals(expectedMessage, validator.validate(imageDto).iterator().next().getMessage());
    }
    //////////////////////////////////////////////////////////////////////////////////


    @Test
    void test_null_data() {
        ProductImageDto imageDto = getValidImageDto();
        imageDto.setData(null);

        assertFalse(validator.validate(imageDto).isEmpty());
        String expectedMessage = "ProductImage's data should not be empty";

        assertEquals(expectedMessage, validator.validate(imageDto).iterator().next().getMessage());
    }

}