package com.gitlab.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

 class RoleDtoTest extends AbstractDtoTest{

    @Test
    void test_valid_text_length() {
        RoleDto roleDto = new RoleDto();
        roleDto.setName("roleText");

        assertTrue(validator.validate(roleDto).isEmpty());
    }

    @Test
    void test_invalid_text_length() {
        RoleDto exampleDto = new RoleDto();
        exampleDto.setName("");

        assertFalse(validator.validate(exampleDto).isEmpty());
    }

    @Test
    void test_invalid_max_text_length() {
        RoleDto exampleDto = new RoleDto();
        exampleDto.setName("a".repeat(256));

        assertFalse(validator.validate(exampleDto).isEmpty());
    }

    @Test
    void test_null_text() {
        RoleDto exampleDto = new RoleDto();
        exampleDto.setName(null);

        assertFalse(validator.validate(exampleDto).isEmpty());
    }

    @Test
    void test_default_message_invalid_length() {
        RoleDto exampleDto = new RoleDto();
        exampleDto.setName("a".repeat(51));
        String expectedMessage = "Length of Role's name should be between 1 and 50 characters";
        String actualMessage = validator.validate(exampleDto).iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void test_default_message_null_text() {
        RoleDto exampleDto = new RoleDto();
        exampleDto.setName(null);
        String expectedMessage = "Role's name should have at least one character";
        String actualMessage = validator.validate(exampleDto).iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
