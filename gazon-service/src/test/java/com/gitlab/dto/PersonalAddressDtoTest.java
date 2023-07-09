package com.gitlab.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonalAddressDtoTest extends AbstractDtoTest {

    private PersonalAddressDto getValidPersonalAddressDto() {
        PersonalAddressDto personalAddress = new PersonalAddressDto();
        personalAddress.setId(1L);
        personalAddress.setAddress("Test Address");
        personalAddress.setDirections("Test Directions");
        personalAddress.setApartment("100");
        personalAddress.setFloor("5");
        personalAddress.setEntrance("1");
        personalAddress.setDoorCode("1234");
        personalAddress.setPostCode("123456");
        return personalAddress;
    }

    @Test
    void test_valid_PersonalAddressDto() {
        assertTrue(validator.validate(getValidPersonalAddressDto()).isEmpty());
    }

    @Test
    void test_invalid_address_length() {
        PersonalAddressDto personalAddress = getValidPersonalAddressDto();
        personalAddress.setAddress("");

        assertFalse(validator.validate(personalAddress).isEmpty());
        String expectedMessage = "Length of address should be between 1 and 255 characters";
        assertEquals(validator.validate(personalAddress).iterator().next().getMessage(), expectedMessage);
    }

    @Test
    void test_invalid_max_address_length() {
        PersonalAddressDto personalAddress = getValidPersonalAddressDto();
        personalAddress.setAddress("a".repeat(256));

        assertFalse(validator.validate(personalAddress).isEmpty());
        String expectedMessage = "Length of address should be between 1 and 255 characters";
        assertEquals(validator.validate(personalAddress).iterator().next().getMessage(), expectedMessage);
    }

    @Test
    void test_null_address() {
        PersonalAddressDto personalAddress = getValidPersonalAddressDto();
        personalAddress.setAddress(null);

        assertFalse(validator.validate(personalAddress).isEmpty());
        String expectedMessage = "Address can not be empty";
        assertEquals(validator.validate(personalAddress).iterator().next().getMessage(), expectedMessage);
    }
    //////////////////////////////////////////////////////////////////////////////////

    @Test
    void test_invalid_max_directions_length() {
        PersonalAddressDto personalAddress = getValidPersonalAddressDto();
        personalAddress.setDirections("a".repeat(256));

        assertFalse(validator.validate(personalAddress).isEmpty());
        String expectedMessage = "Length of directions should not exceed 255 characters";
        assertEquals(validator.validate(personalAddress).iterator().next().getMessage(), expectedMessage);
    }

    @Test
    void test_null_directions() {
        PersonalAddressDto personalAddress = getValidPersonalAddressDto();
        personalAddress.setDirections(null);

        assertTrue(validator.validate(personalAddress).isEmpty());
    }

    @Test
    void test_empty_directions() {
        PersonalAddressDto personalAddress = getValidPersonalAddressDto();
        personalAddress.setDirections("");

        assertTrue(validator.validate(personalAddress).isEmpty());
    }
    //////////////////////////////////////////////////////////////////////////////////

    @Test
    void test_invalid_apartment_length() {
        PersonalAddressDto personalAddress = getValidPersonalAddressDto();
        personalAddress.setApartment("");

        assertFalse(validator.validate(personalAddress).isEmpty());
        String expectedMessage = "Length of apartment should be between 1 and 255 characters";
        assertEquals(validator.validate(personalAddress).iterator().next().getMessage(), expectedMessage);
    }

    @Test
    void test_invalid_max_apartment_length() {
        PersonalAddressDto personalAddress = getValidPersonalAddressDto();
        personalAddress.setApartment("a".repeat(256));

        assertFalse(validator.validate(personalAddress).isEmpty());
        String expectedMessage = "Length of apartment should be between 1 and 255 characters";
        assertEquals(validator.validate(personalAddress).iterator().next().getMessage(), expectedMessage);
    }

    @Test
    void test_null_apartment() {
        PersonalAddressDto personalAddress = getValidPersonalAddressDto();
        personalAddress.setApartment(null);

        assertFalse(validator.validate(personalAddress).isEmpty());
        String expectedMessage = "Apartment can not be empty";
        assertEquals(validator.validate(personalAddress).iterator().next().getMessage(), expectedMessage);
    }
    //////////////////////////////////////////////////////////////////////////////////

    @Test
    void test_invalid_floor_length() {
        PersonalAddressDto personalAddress = getValidPersonalAddressDto();
        personalAddress.setFloor("");

        assertFalse(validator.validate(personalAddress).isEmpty());
        String expectedMessage = "Length of floor should be between 1 and 10 characters";
        assertEquals(validator.validate(personalAddress).iterator().next().getMessage(), expectedMessage);
    }

    @Test
    void test_invalid_max_floor_length() {
        PersonalAddressDto personalAddress = getValidPersonalAddressDto();
        personalAddress.setFloor("a".repeat(11));

        assertFalse(validator.validate(personalAddress).isEmpty());
        String expectedMessage = "Length of floor should be between 1 and 10 characters";
        assertEquals(validator.validate(personalAddress).iterator().next().getMessage(), expectedMessage);
    }

    @Test
    void test_null_floor() {
        PersonalAddressDto personalAddress = getValidPersonalAddressDto();
        personalAddress.setFloor(null);

        assertFalse(validator.validate(personalAddress).isEmpty());
        String expectedMessage = "Floor can not be empty";
        assertEquals(validator.validate(personalAddress).iterator().next().getMessage(), expectedMessage);
    }
    //////////////////////////////////////////////////////////////////////////////////

    @Test
    void test_invalid_entrance_length() {
        PersonalAddressDto personalAddress = getValidPersonalAddressDto();
        personalAddress.setEntrance("");

        assertFalse(validator.validate(personalAddress).isEmpty());
        String expectedMessage = "Length of entrance should be between 1 and 255 characters";
        assertEquals(validator.validate(personalAddress).iterator().next().getMessage(), expectedMessage);
    }

    @Test
    void test_invalid_max_entrance_length() {
        PersonalAddressDto personalAddress = getValidPersonalAddressDto();
        personalAddress.setEntrance("a".repeat(256));

        assertFalse(validator.validate(personalAddress).isEmpty());
        String expectedMessage = "Length of entrance should be between 1 and 255 characters";
        assertEquals(validator.validate(personalAddress).iterator().next().getMessage(), expectedMessage);
    }

    @Test
    void test_null_entrance() {
        PersonalAddressDto personalAddress = getValidPersonalAddressDto();
        personalAddress.setEntrance(null);

        assertFalse(validator.validate(personalAddress).isEmpty());
        String expectedMessage = "Entrance can not be empty";
        assertEquals(validator.validate(personalAddress).iterator().next().getMessage(), expectedMessage);
    }
    //////////////////////////////////////////////////////////////////////////////////

    @Test
    void test_invalid_max_doorCode_length() {
        PersonalAddressDto personalAddress = getValidPersonalAddressDto();
        personalAddress.setDoorCode("a".repeat(51));

        assertFalse(validator.validate(personalAddress).isEmpty());
        String expectedMessage = "Length of door code should not exceed 50 characters";
        assertEquals(validator.validate(personalAddress).iterator().next().getMessage(), expectedMessage);
    }

    @Test
    void test_null_doorCode() {
        PersonalAddressDto personalAddress = getValidPersonalAddressDto();
        personalAddress.setDoorCode(null);

        assertTrue(validator.validate(personalAddress).isEmpty());
    }

    @Test
    void test_empty_doorCode() {
        PersonalAddressDto personalAddress = getValidPersonalAddressDto();
        personalAddress.setDoorCode("");

        assertTrue(validator.validate(personalAddress).isEmpty());
    }
    //////////////////////////////////////////////////////////////////////////////////

    @Test
    void test_invalid_max_postCode_length() {
        PersonalAddressDto personalAddress = getValidPersonalAddressDto();
        personalAddress.setPostCode("a".repeat(13));

        assertFalse(validator.validate(personalAddress).isEmpty());
        String expectedMessage = "Length of post code should not exceed 12 characters";
        assertEquals(validator.validate(personalAddress).iterator().next().getMessage(), expectedMessage);
    }

    @Test
    void test_null_postCode() {
        PersonalAddressDto personalAddress = getValidPersonalAddressDto();
        personalAddress.setPostCode(null);

        assertTrue(validator.validate(personalAddress).isEmpty());
    }

    @Test
    void test_empty_postCode() {
        PersonalAddressDto personalAddress = getValidPersonalAddressDto();
        personalAddress.setPostCode("");

        assertTrue(validator.validate(personalAddress).isEmpty());
    }


}
