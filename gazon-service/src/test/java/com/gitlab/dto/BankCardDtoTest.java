package com.gitlab.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankCardDtoTest extends AbstractDtoTest{

    @Test
    void test_valid_cardNumber_length() {
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setCardNumber("123456789");
        bankCardDto.setDueDate(LocalDate.MIN);
        bankCardDto.setSecurityCode((byte) 12);

        assertTrue(validator.validate(bankCardDto).isEmpty());
    }

    @Test
    void test_invalid_cardNumber_length() {
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setCardNumber("");

        assertFalse(validator.validate(bankCardDto).isEmpty());
    }

    @Test
    void test_invalid_max_cardNumber_length() {
        BankCardDto bankCardDto = new BankCardDto();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 256; i++) sb.append(i);
        bankCardDto.setCardNumber(sb.toString());

        assertFalse(validator.validate(bankCardDto).isEmpty());
    }

    @Test
    void test_default_cardNumber_invalid_length() {
        BankCardDto bankCardDto = new BankCardDto();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 256; i++) sb.append(i);
        bankCardDto.setCardNumber(sb.toString());
        bankCardDto.setDueDate(LocalDate.MIN);
        bankCardDto.setSecurityCode((byte) 12);
        String expectedMessage = "Length of BankCard's cardNumber should be between 8 and 19 characters";
        String actualMessage = validator.validate(bankCardDto).iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void test_null_cardNumber() {
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setCardNumber(null);

        assertFalse(validator.validate(bankCardDto).isEmpty());
    }

    @Test
    void test_default_message_null_cardNumber() {
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setCardNumber(null);
        bankCardDto.setDueDate(LocalDate.MIN);
        bankCardDto.setSecurityCode((byte) 12);
        String expectedMessage = "BankCard's cardNumber should have at least eight characters";
        String actualMessage = validator.validate(bankCardDto).iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void test_null_dueDate() {
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setDueDate(null);

        assertFalse(validator.validate(bankCardDto).isEmpty());
    }

    @Test
    void test_default_message_null_dueDate() {
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setCardNumber("123456789");
        bankCardDto.setDueDate(null);
        bankCardDto.setSecurityCode((byte) 12);
        String expectedMessage = "BankCard's dueDate should not be empty";
        String actualMessage = validator.validate(bankCardDto).iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void test_null_securityCode() {
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setSecurityCode(null);

        assertFalse(validator.validate(bankCardDto).isEmpty());
    }

    @Test
    void test_default_message_null_securityCode() {
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setCardNumber("123456789");
        bankCardDto.setDueDate(LocalDate.MIN);
        bankCardDto.setSecurityCode(null);
        String expectedMessage = "BankCard's securityCode should not be empty";
        String actualMessage = validator.validate(bankCardDto).iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
