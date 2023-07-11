package com.gitlab.dto;

import com.gitlab.model.BankCard;
import com.gitlab.model.Passport;
import com.gitlab.model.Role;
import com.gitlab.model.ShippingAddress;
import com.gitlab.model.enums.Gender;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDtoTest extends AbstractDtoTest {

    @Test
    void test_valid_text_length() {
        UserDto userDto = generateUser();
        assertTrue(validator.validate(userDto).isEmpty());
    }

    @Test
    void test_invalid_text_length() {
        UserDto userDto = new UserDto();
        userDto.setEmail("");
        userDto.setPassword("");
        userDto.setSecurityQuestion("");
        userDto.setAnswerQuestion("");
        userDto.setFirstName("");
        userDto.setLastName("");
        userDto.setPhoneNumber("");
        assertFalse(validator.validate(userDto).isEmpty());
    }

    @Test
    void test_invalid_max_text_length() {
        UserDto userDto = new UserDto();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 256; i++) sb.append(i);
        userDto.setEmail(sb.toString());
        userDto.setPassword(sb.toString());
        userDto.setSecurityQuestion(sb.toString());
        userDto.setAnswerQuestion(sb.toString());
        userDto.setFirstName(sb.toString());
        userDto.setLastName(sb.toString());
        userDto.setPhoneNumber(sb.toString());
        assertFalse(validator.validate(userDto).isEmpty());
    }

    @Test
    void test_null_text() {
        UserDto userDto = new UserDto();
        userDto.setEmail(null);
        userDto.setPassword(null);
        userDto.setSecurityQuestion(null);
        userDto.setAnswerQuestion(null);
        userDto.setFirstName(null);
        userDto.setLastName(null);
        userDto.setPhoneNumber(null);
        assertFalse(validator.validate(userDto).isEmpty());
    }

    @Test
    void test_default_message_invalid_length_email() {
        UserDto userDto = generateUser();

        userDto.setEmail("a".repeat(256));
        String expectedMessage = "Length of User's email should be between 1 and 255 characters";
        String actualMessage = validator.validate(userDto).iterator().next().getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void test_default_message_invalid_length_password() {
        UserDto userDto = generateUser();

        userDto.setPassword("a".repeat(256));
        String expectedMessage = "Length of User's password should be between 1 and 255 characters";
        String actualMessage = validator.validate(userDto).iterator().next().getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void test_default_message_invalid_length_securityQuestion() {
        UserDto userDto = generateUser();

        userDto.setSecurityQuestion("a".repeat(256));
        String expectedMessage = "Length of User's securityQuestion should be between 1 and 255 characters";
        String actualMessage = validator.validate(userDto).iterator().next().getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void test_default_message_invalid_length_answerQuestion() {
        UserDto userDto = generateUser();

        userDto.setAnswerQuestion("a".repeat(256));
        String expectedMessage = "Length of User's answerQuestion should be between 1 and 255 characters";
        String actualMessage = validator.validate(userDto).iterator().next().getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void test_default_message_invalid_length_firstName() {
        UserDto userDto = generateUser();

        userDto.setFirstName("a".repeat(256));
        String expectedMessage = "Length of User's firstName should be between 1 and 255 characters";
        String actualMessage = validator.validate(userDto).iterator().next().getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void test_default_message_invalid_length_lastName() {
        UserDto userDto = generateUser();

        userDto.setLastName("a".repeat(256));
        String expectedMessage = "Length of User's lastName should be between 1 and 255 characters";
        String actualMessage = validator.validate(userDto).iterator().next().getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void test_default_message_invalid_length_phoneNumber() {
        UserDto userDto = generateUser();

        userDto.setPhoneNumber("a".repeat(256));
        String expectedMessage = "Length of User's phoneNumber should be between 1 and 255 characters";
        String actualMessage = validator.validate(userDto).iterator().next().getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void test_default_message_null_text_email() {
        UserDto userDto = generateUser();

        userDto.setEmail(null);
        String expectedMessage = "User's email should have at least one character";
        String actualMessage = validator.validate(userDto).iterator().next().getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void test_default_message_null_text_password() {
        UserDto userDto = generateUser();

        userDto.setPassword(null);
        String expectedMessage = "User's password should have at least one character";
        String actualMessage = validator.validate(userDto).iterator().next().getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void test_default_message_null_text_securityQuestion() {
        UserDto userDto = generateUser();

        userDto.setSecurityQuestion(null);
        String expectedMessage = "User's securityQuestion should have at least one character";
        String actualMessage = validator.validate(userDto).iterator().next().getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void test_default_message_null_text_answerQuestion() {
        UserDto userDto = generateUser();

        userDto.setAnswerQuestion(null);
        String expectedMessage = "User's answerQuestion should have at least one character";
        String actualMessage = validator.validate(userDto).iterator().next().getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void test_default_message_null_text_firstName() {
        UserDto userDto = generateUser();

        userDto.setFirstName(null);
        String expectedMessage = "User's firstName should have at least one character";
        String actualMessage = validator.validate(userDto).iterator().next().getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void test_default_message_null_text_lastName() {
        UserDto userDto = generateUser();

        userDto.setLastName(null);
        String expectedMessage = "User's lastName should have at least one character";
        String actualMessage = validator.validate(userDto).iterator().next().getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void test_default_message_null_object_birthDate() {
        UserDto userDto = generateUser();

        userDto.setBirthDate(null);
        String expectedMessage = "User birthDate cannot be null";
        String actualMessage = validator.validate(userDto).iterator().next().getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void test_default_message_null_object_gender() {
        UserDto userDto = generateUser();

        userDto.setGender(null);
        String expectedMessage = "User gender cannot be null";
        String actualMessage = validator.validate(userDto).iterator().next().getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void test_default_message_null_text_phoneNumber() {
        UserDto userDto = generateUser();

        userDto.setPhoneNumber(null);
        String expectedMessage = "User's phoneNumber should have at least one character";
        String actualMessage = validator.validate(userDto).iterator().next().getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void test_default_message_null_object_passport() {
        UserDto userDto = generateUser();

        userDto.setPassport(null);
        String expectedMessage = "User passport cannot be null";
        String actualMessage = validator.validate(userDto).iterator().next().getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void test_default_message_null_object_bankCards() {
        UserDto userDto = generateUser();

        userDto.setBankCards(null);
        String expectedMessage = "User bankCards cannot be null";
        String actualMessage = validator.validate(userDto).iterator().next().getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void test_default_message_null_object_roles() {
        UserDto userDto = generateUser();

        userDto.setRoles(null);
        String expectedMessage = "User roles cannot be null";
        String actualMessage = validator.validate(userDto).iterator().next().getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    private UserDto generateUser() {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(1L, "ROLE_ADMIN"));
        Set<BankCard> bankCardSet = new HashSet<>();
        bankCardSet.add(new BankCard(1L, "0000000000000000", LocalDate.of(1900, 1, 1), 777));
        Set<ShippingAddress> shippingAddressesSet = new HashSet<>();
        shippingAddressesSet.add(new ShippingAddress(1L, "address", "directions"));
        UserDto userDto = new UserDto();

        userDto.setEmail("mail@mail.ru");
        userDto.setPassword("user");
        userDto.setSecurityQuestion("answer");
        userDto.setAnswerQuestion("question");
        userDto.setFirstName("user");
        userDto.setLastName("user");
        userDto.setBirthDate(LocalDate.of(1900, 1, 1));
        userDto.setGender(Gender.MALE);
        userDto.setPhoneNumber("89007777777");
        userDto.setPassport(new Passport(1L, "7777777777"));
        userDto.setShippingAddress(shippingAddressesSet);
        userDto.setBankCards(bankCardSet);
        userDto.setRoles(roleSet);
             return userDto;
    }
}
