package com.gitlab.dto;

import com.gitlab.model.PickupPoint;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PickupPointDtoTest extends AbstractDtoTest {

    private PickupPointDto getValidPickupPointDto() {
        PickupPointDto pickupPointDto = new PickupPointDto();
        pickupPointDto.setId(1L);
        pickupPointDto.setAddress("Test Address");
        pickupPointDto.setDirections("Test Directions");
        pickupPointDto.setShelfLifeDays((byte) 5);
        return pickupPointDto;
    }

    @Test
    void test_valid_PickupPointDto() {
        assertTrue(validator.validate(getValidPickupPointDto()).isEmpty());
    }

    @Test
    void test_valid_PickupPointDto_with_features() {
        PickupPointDto pickupPointDto = getValidPickupPointDto();
        pickupPointDto.setPickupPointFeatures(Set.of(PickupPoint.PickupPointFeatures.values()));
        assertTrue(validator.validate(pickupPointDto).isEmpty());
    }

    @Test
    void test_invalid_address_length() {
        PickupPointDto pickupPointDto = getValidPickupPointDto();
        pickupPointDto.setAddress("");

        assertFalse(validator.validate(pickupPointDto).isEmpty());
        String expectedMessage = "Length of address should be between 1 and 255 characters";
        assertEquals(validator.validate(pickupPointDto).iterator().next().getMessage(), expectedMessage);
    }

    @Test
    void test_invalid_max_address_length() {
        PickupPointDto pickupPointDto = getValidPickupPointDto();
        pickupPointDto.setAddress("a".repeat(256));

        assertFalse(validator.validate(pickupPointDto).isEmpty());
        String expectedMessage = "Length of address should be between 1 and 255 characters";
        assertEquals(validator.validate(pickupPointDto).iterator().next().getMessage(), expectedMessage);
    }

    @Test
    void test_null_address() {
        PickupPointDto pickupPointDto = getValidPickupPointDto();
        pickupPointDto.setAddress(null);

        assertFalse(validator.validate(pickupPointDto).isEmpty());
        String expectedMessage = "Address can not be empty";
        assertEquals(validator.validate(pickupPointDto).iterator().next().getMessage(), expectedMessage);
    }
    //////////////////////////////////////////////////////////////////////////////////

    @Test
    void test_invalid_max_directions_length() {
        PickupPointDto pickupPointDto = getValidPickupPointDto();
        pickupPointDto.setDirections("a".repeat(256));

        assertFalse(validator.validate(pickupPointDto).isEmpty());
        String expectedMessage = "Length of directions should not exceed 255 characters";
        assertEquals(validator.validate(pickupPointDto).iterator().next().getMessage(), expectedMessage);
    }

    @Test
    void test_null_directions() {
        PickupPointDto pickupPointDto = getValidPickupPointDto();
        pickupPointDto.setDirections(null);

        assertTrue(validator.validate(pickupPointDto).isEmpty());
    }

    @Test
    void test_empty_directions() {
        PickupPointDto pickupPointDto = getValidPickupPointDto();
        pickupPointDto.setDirections("");

        assertTrue(validator.validate(pickupPointDto).isEmpty());
    }
    //////////////////////////////////////////////////////////////////////////////////

    @Test
    void test_invalid_shelfLifeDays_value() {
        PickupPointDto pickupPointDto = getValidPickupPointDto();
        pickupPointDto.setShelfLifeDays((byte) 0);

        assertFalse(validator.validate(pickupPointDto).isEmpty());
        String expectedMessage = "Shelf life should be between 1 and 30 days";
        assertEquals(validator.validate(pickupPointDto).iterator().next().getMessage(), expectedMessage);
    }

    @Test
    void test_invalid_max_shelfLifeDays_value() {
        PickupPointDto pickupPointDto = getValidPickupPointDto();
        pickupPointDto.setShelfLifeDays((byte) 31);

        assertFalse(validator.validate(pickupPointDto).isEmpty());
        String expectedMessage = "Shelf life should be between 1 and 30 days";
        assertEquals(validator.validate(pickupPointDto).iterator().next().getMessage(), expectedMessage);
    }

    @Test
    void test_null_shelfLifeDays() {
        PickupPointDto pickupPointDto = getValidPickupPointDto();
        pickupPointDto.setShelfLifeDays(null);

        assertFalse(validator.validate(pickupPointDto).isEmpty());
        String expectedMessage = "Shelf life days can not be empty";
        assertEquals(validator.validate(pickupPointDto).iterator().next().getMessage(), expectedMessage);
    }
}
