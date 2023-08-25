package com.gitlab.dto;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class StoreDtoTest extends AbstractDtoTest {

    private StoreDto getValidStoreDto() {
        Long id = 1L;
        Long ownerId = 1L;
        var storeDto = new StoreDto();
        storeDto.setOwnerId(ownerId);
        storeDto.setId(id);
        return storeDto;
    }

    @Test
    void test_valid_storeDto() {

        assertTrue(validator.validate(getValidStoreDto()).isEmpty());
    }
}
