package com.gitlab.dto;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SelectedProductDtoTest extends AbstractDtoTest{

    private SelectedProductDto getValidSelectedProductDto() {
        SelectedProductDto selectedProductDto = new SelectedProductDto();
        selectedProductDto.setProductId(1L);
        selectedProductDto.setCount(1);
        return selectedProductDto;
    }

    @Test
    void test_valid_selectedProductDto() {

        assertTrue(validator.validate(getValidSelectedProductDto()).isEmpty());
    }

    @Test
    void test_invalid_count_value() {
        SelectedProductDto selectedProductDto = getValidSelectedProductDto();
        selectedProductDto.setCount(0);

        assertFalse(validator.validate(selectedProductDto).isEmpty());
        String expectedMessage = "SelectedProduct's count should be between 1 and 2147483333";
        assertEquals(expectedMessage, validator.validate(selectedProductDto).iterator().next().getMessage());
    }


    @Test
    void test_invalid_max_count_value() {
        SelectedProductDto selectedProductDto = getValidSelectedProductDto();
        selectedProductDto.setCount(Integer.MAX_VALUE);

        assertFalse(validator.validate(selectedProductDto).isEmpty());
        String expectedMessage = "SelectedProduct's count should be between 1 and 2147483333";
        assertEquals(expectedMessage, validator.validate(selectedProductDto).iterator().next().getMessage());
    }


    @Test
    void test_null_count() {
        SelectedProductDto selectedProductDto = getValidSelectedProductDto();
        selectedProductDto.setCount(null);

        assertFalse(validator.validate(selectedProductDto).isEmpty());
        String expectedMessage = "SelectedProduct's count should not be empty";
        assertEquals(expectedMessage, validator.validate(selectedProductDto).iterator().next().getMessage());
    }
    //////////////////////////////////////////////////////////////////////////////////
    @Test
    void test_null_productId() {
        SelectedProductDto selectedProductDto = getValidSelectedProductDto();
        selectedProductDto.setProductId(null);

        assertFalse(validator.validate(selectedProductDto).isEmpty());
        String expectedMessage = "SelectedProduct's productId should not be empty";
        assertEquals(expectedMessage, validator.validate(selectedProductDto).iterator().next().getMessage());
    }
    //////////////////////////////////////////////////////////////////////////////////


}
