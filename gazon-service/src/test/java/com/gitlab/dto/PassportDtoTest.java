package com.gitlab.dto;

import com.gitlab.model.Passport;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class PassportDtoTest extends AbstractDtoTest {

    private final PassportDto validPassportDto = new PassportDto();

    {
        validPassportDto.setId(1L);
        validPassportDto.setCitizenship(Passport.Citizenship.RUSSIA);
        validPassportDto.setFirstName("firstName");
        validPassportDto.setLastName("lastName");
        validPassportDto.setPatronym("patronym");
        validPassportDto.setBirthDate(LocalDate.of(2000, 1, 1));
        validPassportDto.setIssueDate(LocalDate.of(2015,1,1));
        validPassportDto.setPassportNumber("1111 111111");
        validPassportDto.setIssuer("Otdel police #1");
        validPassportDto.setIssuerNumber("111-111");
    }

    @Test
    void test_valid_passportDto() {
        assertTrue(validator.validate(validPassportDto).isEmpty());
    }

    /**
     Testing invalid length and default message
     */
    @Test
    void test_invalid_firstName_length_and_default_message() {
        PassportDto passportDto = validPassportDto;
        passportDto.setFirstName("");
        assertFalse(validator.validate(passportDto).isEmpty());

        String patternMessage = "first name can contain only letters";
        String sizeMessage = "Passport's first name should have at least two characters and not exceed 15";
        String actualMessages = validator.validate(passportDto).toString();

        assertTrue(actualMessages.contains(sizeMessage));
        assertTrue(actualMessages.contains(patternMessage));
    }

    @Test
    void test_invalid_lastName_length_and_default_message() {
        PassportDto passportDto = validPassportDto;
        passportDto.setLastName("");
        assertFalse(validator.validate(passportDto).isEmpty());

        String patternMessage = "last name can contain only letters";
        String sizeMessage = "Passport's lastname should have at least two characters and not exceed 25";
        String actualMessages = validator.validate(passportDto).toString();

        assertTrue(actualMessages.contains(sizeMessage));
        assertTrue(actualMessages.contains(patternMessage));
    }

    @Test
    void test_invalid_patronym_length_and_default_message() {
        PassportDto passportDto = validPassportDto;
        passportDto.setPatronym("");
        assertFalse(validator.validate(passportDto).isEmpty());

        String patternMessage = "patronym can contain only letters";
        String sizeMessage = "Passport patronym should have at least two characters and not exceed 25";
        String actualMessages = validator.validate(passportDto).toString();

        assertTrue(actualMessages.contains(sizeMessage));
        assertTrue(actualMessages.contains(patternMessage));
    }

    @Test
    void test_invalid_passportNumber_length_and_default_message() {
        PassportDto passportDto = validPassportDto;
        passportDto.setPassportNumber("");
        assertFalse(validator.validate(passportDto).isEmpty());

        String patternMessage = "Passport issuer number must be in \"**** ******\" format";
        String sizeMessage = "Passport number must consist of 11 characters";
        String actualMessages = validator.validate(passportDto).toString();

        assertTrue(actualMessages.contains(sizeMessage));
        assertTrue(actualMessages.contains(patternMessage));
    }

    @Test
    void test_invalid_issuer_length_and_default_message() {
        PassportDto passportDto = validPassportDto;
        passportDto.setIssuer(" ");
        assertFalse(validator.validate(passportDto).isEmpty());

        String sizeMessage = "Passport's issuer cannot less than ten characters and not exceed 255";
        String actualMessages = validator.validate(passportDto).iterator().next().getMessage();

        assertEquals(actualMessages, sizeMessage);
    }

    @Test
    void test_invalid_issuerNumber_length_and_default_message() {
        PassportDto passportDto = validPassportDto;
        passportDto.setIssuerNumber("");
        assertFalse(validator.validate(passportDto).isEmpty());

        String patternMessage = "Passport's issuer number must be in \"***-***\" format";
        String sizeMessage = "Passport number must consist of 7 characters";
        String actualMessages = validator.validate(passportDto).toString();

        assertTrue(actualMessages.contains(sizeMessage));
        assertTrue(actualMessages.contains(patternMessage));
    }

    /**
     * Testing invalid max length and default message
     */
    @Test
    void test_invalid_max_firstName_length_and_default_message() {
        PassportDto passportDto = validPassportDto;

        passportDto.setFirstName("ы".repeat(16));
        assertFalse(validator.validate(passportDto).isEmpty());

        String sizeMessage = "Passport's first name should have at least two characters and not exceed 15";
        String actualMessages = validator.validate(passportDto).iterator().next().getMessage();
        assertEquals(actualMessages, sizeMessage);
    }

    @Test
    void test_invalid_max_lastName_length_and_default_message() {
        PassportDto passportDto = validPassportDto;

        passportDto.setLastName("ы".repeat(26));
        assertFalse(validator.validate(passportDto).isEmpty());

        String sizeMessage = "Passport's lastname should have at least two characters and not exceed 25";
        String actualMessages = validator.validate(passportDto).iterator().next().getMessage();
        assertEquals(actualMessages, sizeMessage);
    }

    @Test
    void test_invalid_max_patronym_length_and_default_message() {
        PassportDto passportDto = validPassportDto;

        passportDto.setPatronym("ы".repeat(26));
        assertFalse(validator.validate(passportDto).isEmpty());

        String sizeMessage = "Passport patronym should have at least two characters and not exceed 25";
        String actualMessages = validator.validate(passportDto).iterator().next().getMessage();
        assertEquals(actualMessages, sizeMessage);
    }

    @Test
    void test_invalid_max_passportNumber_length_and_default_message() {
        PassportDto passportDto = validPassportDto;

        passportDto.setPassportNumber("111111 1111111111");
        assertFalse(validator.validate(passportDto).isEmpty());

        String sizeMessage = "Passport number must consist of 11 characters";
        String actualMessages = validator.validate(passportDto).iterator().next().getMessage();
        assertEquals(actualMessages, sizeMessage);
    }

    @Test
    void test_invalid_max_issuer_length_and_default_message() {
        PassportDto passportDto = validPassportDto;

        passportDto.setIssuer("ы".repeat(256));
        assertFalse(validator.validate(passportDto).isEmpty());

        String sizeMessage = "Passport's issuer cannot less than ten characters and not exceed 255";
        String actualMessages = validator.validate(passportDto).iterator().next().getMessage();
        assertEquals(actualMessages, sizeMessage);
    }

    @Test
    void test_invalid_max_issuerNumber_length_and_default_message() {
        PassportDto passportDto = validPassportDto;

        passportDto.setIssuerNumber(" ");
        assertFalse(validator.validate(passportDto).isEmpty());

        String sizeMessage = "Passport number must consist of 7 characters";
        String actualMessages = validator.validate(passportDto).iterator().next().getMessage();
        assertEquals(actualMessages, sizeMessage);
    }
    /**
     Testing null and default empty message
     */
    @Test
    void test_null_citizenship_and_empty_default_message() {
        PassportDto passportDto = validPassportDto;
        passportDto.setCitizenship(null);

        String emptyMessage = "Passport's citizenship shouldn't be empty";
        String actualMessages = validator.validate(passportDto).iterator().next().getMessage();

        assertEquals(emptyMessage, actualMessages);
        assertFalse(validator.validate(passportDto).isEmpty());
    }

    @Test
    void test_null_firstName() {
        PassportDto passportDto = validPassportDto;
        passportDto.setFirstName(null);

        String emptyMessage = "Passport's first name shouldn't be empty";
        String actualMessages = validator.validate(passportDto).iterator().next().getMessage();

        assertEquals(emptyMessage, actualMessages);
        assertFalse(validator.validate(passportDto).isEmpty());
    }

    @Test
    void test_null_lastName() {
        PassportDto passportDto = validPassportDto;
        passportDto.setLastName(null);

        String emptyMessage = "Passport's lastname shouldn't be empty";
        String actualMessages = validator.validate(passportDto).iterator().next().getMessage();

        assertEquals(emptyMessage, actualMessages);
        assertFalse(validator.validate(passportDto).isEmpty());
    }

    @Test
    void test_null_birthDate() {
        PassportDto passportDto = validPassportDto;
        passportDto.setBirthDate(null);

        String emptyMessage = "Passport's birth date shouldn't be empty";
        String actualMessages = validator.validate(passportDto).iterator().next().getMessage();

        assertEquals(emptyMessage, actualMessages);
        assertFalse(validator.validate(passportDto).isEmpty());
    }

    @Test
    void test_null_issueDate() {
        PassportDto passportDto = validPassportDto;
        passportDto.setIssueDate(null);

        String emptyMessage = "Passport's issue date shouldn't be empty";
        String actualMessages = validator.validate(passportDto).iterator().next().getMessage();

        assertEquals(emptyMessage, actualMessages);
        assertFalse(validator.validate(passportDto).isEmpty());
    }

    @Test
    void test_null_passportNumber() {
        PassportDto passportDto = validPassportDto;
        passportDto.setPassportNumber(null);

        String emptyMessage = "Passport number shouldn't be empty";
        String actualMessages = validator.validate(passportDto).iterator().next().getMessage();

        assertEquals(emptyMessage, actualMessages);
        assertFalse(validator.validate(passportDto).isEmpty());
    }

    @Test
    void test_null_issuer() {
        PassportDto passportDto = validPassportDto;
        passportDto.setIssuer(null);

        String emptyMessage = "Passport's issuer shouldn't be empty";
        String actualMessages = validator.validate(passportDto).iterator().next().getMessage();

        assertEquals(emptyMessage, actualMessages);
        assertFalse(validator.validate(passportDto).isEmpty());
    }

    @Test
    void test_null_issuerNumber() {
        PassportDto passportDto = validPassportDto;
        passportDto.setIssuerNumber(null);

        String emptyMessage = "Passport's issuer number shouldn't be empty";
        String actualMessages = validator.validate(passportDto).iterator().next().getMessage();

        assertEquals(emptyMessage, actualMessages);
        assertFalse(validator.validate(passportDto).isEmpty());
    }

}
