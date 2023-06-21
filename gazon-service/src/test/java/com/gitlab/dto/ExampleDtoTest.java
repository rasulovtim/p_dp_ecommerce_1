package com.gitlab.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExampleDtoTest extends AbstractDtoTest {

    @Test
    void test_valid_text_length() {
        ExampleDto exampleDto = new ExampleDto();
        exampleDto.setExampleText("ExampleText");

        assertTrue(validator.validate(exampleDto).isEmpty());
    }

    @Test
    void test_invalid_text_length() {
        ExampleDto exampleDto = new ExampleDto();
        exampleDto.setExampleText("");

        assertFalse(validator.validate(exampleDto).isEmpty());
    }

    @Test
    void test_invalid_max_text_length() {
        ExampleDto exampleDto = new ExampleDto();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 256; i++) sb.append(i);
        exampleDto.setExampleText(sb.toString());

        assertFalse(validator.validate(exampleDto).isEmpty());
    }

    @Test
    void test_null_text() {
        ExampleDto exampleDto = new ExampleDto();
        exampleDto.setExampleText(null);

        assertFalse(validator.validate(exampleDto).isEmpty());
    }

    @Test
    void test_default_message_invalid_length() {
        ExampleDto exampleDto = new ExampleDto();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 256; i++) sb.append(i);
        exampleDto.setExampleText(sb.toString());
        String expectedMessage = "Length of Example's exampleText should be between 1 and 255 characters";
        String actualMessage = validator.validate(exampleDto).iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void test_default_message_null_text() {
        ExampleDto exampleDto = new ExampleDto();
        exampleDto.setExampleText(null);
        String expectedMessage = "Example's exampleText should have at least one character";
        String actualMessage = validator.validate(exampleDto).iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}