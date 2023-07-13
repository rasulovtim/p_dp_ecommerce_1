package com.gitlab.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductImageDtoTest extends AbstractDtoTest {


    @Test
    void test_default_message_null_image_productID() {
        ProductImageDto productImageDto = new ProductImageDto();
        productImageDto.setProductId(null);
        productImageDto.setName("image1");
        productImageDto.setData(new byte[1]);
        String expectedMessage = "ProductImage's productId should not be empty";
        String actualMessage = validator.validate(productImageDto).iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    void test_default_message_null_image_name() {
        ProductImageDto productImageDto = new ProductImageDto();
        productImageDto.setProductId(1L);
        productImageDto.setName(null);
        productImageDto.setData(new byte[1]);
        String expectedMessage = "ProductImage's name should not be empty";
        String actualMessage = validator.validate(productImageDto).iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    void test_default_message_null_image_data() {
        ProductImageDto productImageDto = new ProductImageDto();
        productImageDto.setProductId(1L);
        productImageDto.setName("image1");
        productImageDto.setData(null);
        String expectedMessage = "ProductImage's data should not be empty";
        String actualMessage = validator.validate(productImageDto).iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
    }


}