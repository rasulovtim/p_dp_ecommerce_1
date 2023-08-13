package com.gitlab.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostomatDtoTest extends AbstractDtoTest {

    private PostomatDto getValidPostomatDto() {
        var postomatDto = new PostomatDto();
        postomatDto.setId(1L);
        postomatDto.setAddress("Test Address");
        postomatDto.setDirections("Test Directions");
        postomatDto.setShelfLifeDays((byte) 5);
        return postomatDto;
    }

    @Test
    void test_valid_PostomatDto() {
        assertTrue(validator.validate(getValidPostomatDto()).isEmpty());
    }

    @Test
    void test_invalid_address_length() {
        PostomatDto postomatDto = getValidPostomatDto();
        postomatDto.setAddress("");

        assertFalse(validator.validate(postomatDto).isEmpty());
        String expectedMessage = "Length of address should be between 1 and 255 characters";
        assertEquals(validator.validate(postomatDto).iterator().next().getMessage(), expectedMessage);
    }

    @Test
    void test_invalid_max_address_length() {
        PostomatDto postomatDto = getValidPostomatDto();
        postomatDto.setAddress("a".repeat(256));

        assertFalse(validator.validate(postomatDto).isEmpty());
        String expectedMessage = "Length of address should be between 1 and 255 characters";
        assertEquals(validator.validate(postomatDto).iterator().next().getMessage(), expectedMessage);
    }

    @Test
    void test_null_address() {
        PostomatDto postomatDto = getValidPostomatDto();
        postomatDto.setAddress(null);

        assertFalse(validator.validate(postomatDto).isEmpty());
        String expectedMessage = "Address can not be empty";
        assertEquals(validator.validate(postomatDto).iterator().next().getMessage(), expectedMessage);
    }
    //////////////////////////////////////////////////////////////////////////////////

    @Test
    void test_invalid_max_directions_length() {
        PostomatDto postomatDto = getValidPostomatDto();
        postomatDto.setDirections("a".repeat(256));

        assertFalse(validator.validate(postomatDto).isEmpty());
        String expectedMessage = "Length of directions should not exceed 255 characters";
        assertEquals(validator.validate(postomatDto).iterator().next().getMessage(), expectedMessage);
    }

    @Test
    void test_null_directions() {
        PostomatDto postomatDto = getValidPostomatDto();
        postomatDto.setDirections(null);

        assertTrue(validator.validate(postomatDto).isEmpty());
    }

    @Test
    void test_empty_directions() {
        PostomatDto postomatDto = getValidPostomatDto();
        postomatDto.setDirections("");

        assertTrue(validator.validate(postomatDto).isEmpty());
    }
    //////////////////////////////////////////////////////////////////////////////////

    @Test
    void test_invalid_shelfLifeDays_value() {
        PostomatDto postomatDto = getValidPostomatDto();
        postomatDto.setShelfLifeDays((byte) 0);

        assertFalse(validator.validate(postomatDto).isEmpty());
        String expectedMessage = "Shelf life should be between 1 and 30 days";
        assertEquals(validator.validate(postomatDto).iterator().next().getMessage(), expectedMessage);
    }

    @Test
    void test_invalid_max_shelfLifeDays_value() {
        PostomatDto postomatDto = getValidPostomatDto();
        postomatDto.setShelfLifeDays((byte) 31);

        assertFalse(validator.validate(postomatDto).isEmpty());
        String expectedMessage = "Shelf life should be between 1 and 30 days";
        assertEquals(validator.validate(postomatDto).iterator().next().getMessage(), expectedMessage);
    }

    @Test
    void test_null_shelfLifeDays() {
        PostomatDto postomatDto = getValidPostomatDto();
        postomatDto.setShelfLifeDays(null);

        assertFalse(validator.validate(postomatDto).isEmpty());
        String expectedMessage = "Shelf life days can not be empty";
        assertEquals(validator.validate(postomatDto).iterator().next().getMessage(), expectedMessage);
    }
}
