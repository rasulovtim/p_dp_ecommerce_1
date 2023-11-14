package com.gitlab.mapper;

import com.gitlab.dto.PickupPointDto;
import com.gitlab.enums.PickupPointFeatures;
import com.gitlab.model.PickupPoint;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PickupPointMapperTest {

    private final PickupPointMapper mapper = Mappers.getMapper(PickupPointMapper.class);

    @Test
    void should_should_map_PickupPoint_to_Dto() {
        PickupPoint pickupPoint = new PickupPoint();
        pickupPoint.setId(1L);
        pickupPoint.setAddress("Moscow,Russia");
        pickupPoint.setDirections("Some directions");
        pickupPoint.setShelfLifeDays((byte) 5);
        pickupPoint.setPickupPointFeatures(Set.of(PickupPointFeatures.values()));

        PickupPointDto actualResult = mapper.toDto(pickupPoint);

        assertNotNull(actualResult);
        assertFalse(pickupPoint.getPickupPointFeatures().isEmpty());
        assertEquals(pickupPoint.getId(), actualResult.getId());
        assertEquals(pickupPoint.getAddress(), actualResult.getAddress());
        assertEquals(pickupPoint.getDirections(), actualResult.getDirections());
        assertEquals(pickupPoint.getShelfLifeDays(), actualResult.getShelfLifeDays());
        assertTrue(actualResult.getPickupPointFeatures().containsAll(pickupPoint.getPickupPointFeatures()));
    }

    @Test
    void should_should_map_PickupPointDto_to_Entity() {
        PickupPointDto pickupPointDto = new PickupPointDto();
        pickupPointDto.setId(1L);
        pickupPointDto.setAddress("Moscow,Russia");
        pickupPointDto.setDirections("Some directions");
        pickupPointDto.setShelfLifeDays((byte) 5);
        pickupPointDto.setPickupPointFeatures(Set.of(PickupPointFeatures.values()));

        PickupPoint actualResult = mapper.toEntity(pickupPointDto);

        assertNotNull(actualResult);
        assertFalse(pickupPointDto.getPickupPointFeatures().isEmpty());
        assertEquals(pickupPointDto.getId(), actualResult.getId());
        assertEquals(pickupPointDto.getAddress(), actualResult.getAddress());
        assertEquals(pickupPointDto.getDirections(), actualResult.getDirections());
        assertEquals(pickupPointDto.getShelfLifeDays(), actualResult.getShelfLifeDays());
        assertTrue(actualResult.getPickupPointFeatures().containsAll(pickupPointDto.getPickupPointFeatures()));
    }
}
