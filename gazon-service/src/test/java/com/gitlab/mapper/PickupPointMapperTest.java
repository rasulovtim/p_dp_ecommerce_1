package com.gitlab.mapper;

import com.gitlab.dto.PickupPointDto;
import com.gitlab.enums.PickupPointFeatures;
import com.gitlab.model.PickupPoint;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PickupPointMapperTest {

    private final PickupPointMapper mapper = Mappers.getMapper(PickupPointMapper.class);

    @Test
    void should_should_map_PickupPoint_to_Dto() {
        PickupPoint pickupPoint = getPickupPoint(1L);

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
        PickupPointDto pickupPointDto = getPickupPointDto(1L);

        PickupPoint actualResult = mapper.toEntity(pickupPointDto);

        assertNotNull(actualResult);
        assertFalse(pickupPointDto.getPickupPointFeatures().isEmpty());
        assertEquals(pickupPointDto.getId(), actualResult.getId());
        assertEquals(pickupPointDto.getAddress(), actualResult.getAddress());
        assertEquals(pickupPointDto.getDirections(), actualResult.getDirections());
        assertEquals(pickupPointDto.getShelfLifeDays(), actualResult.getShelfLifeDays());
        assertTrue(actualResult.getPickupPointFeatures().containsAll(pickupPointDto.getPickupPointFeatures()));
    }

    @Test
    void should_map_pickupPointList_to_DtoList() {
        List<PickupPoint> pickupPointList = List.of(getPickupPoint(1L), getPickupPoint(2L), getPickupPoint(3L));

        List<PickupPointDto> pickupPointDtoList = mapper.toDtoList(pickupPointList);

        assertNotNull(pickupPointDtoList);
        assertEquals(pickupPointList.size(), pickupPointList.size());
        for (int i = 0; i < pickupPointDtoList.size(); i++) {
            PickupPointDto dto = pickupPointDtoList.get(i);
            PickupPoint entity = pickupPointList.get(i);
            assertFalse(dto.getPickupPointFeatures().isEmpty());
            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getAddress(), entity.getAddress());
            assertEquals(dto.getDirections(), entity.getDirections());
            assertEquals(dto.getShelfLifeDays(), entity.getShelfLifeDays());
            assertTrue(entity.getPickupPointFeatures().containsAll(dto.getPickupPointFeatures()));
        }
    }

    @Test
    void should_map_pickupPointDtoList_to_EntityList() {
        List<PickupPointDto> pickupPointDtoList = List.of(getPickupPointDto(1L), getPickupPointDto(2L), getPickupPointDto(3L));

        List<PickupPoint> pickupPointList = mapper.toEntityList(pickupPointDtoList);

        assertNotNull(pickupPointList);
        assertEquals(pickupPointList.size(), pickupPointList.size());
        for (int i = 0; i < pickupPointList.size(); i++) {
            PickupPointDto dto = pickupPointDtoList.get(i);
            PickupPoint entity = pickupPointList.get(i);
            assertFalse(dto.getPickupPointFeatures().isEmpty());
            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getAddress(), entity.getAddress());
            assertEquals(dto.getDirections(), entity.getDirections());
            assertEquals(dto.getShelfLifeDays(), entity.getShelfLifeDays());
            assertTrue(entity.getPickupPointFeatures().containsAll(dto.getPickupPointFeatures()));
        }
    }

    @NotNull
    private PickupPoint getPickupPoint(Long id) {
        PickupPoint pickupPoint = new PickupPoint();
        pickupPoint.setId(id);
        pickupPoint.setAddress("Moscow,Russia" + id);
        pickupPoint.setDirections("Some directions" + id);
        pickupPoint.setShelfLifeDays((byte) (5 / id));
        pickupPoint.setPickupPointFeatures(Set.of(PickupPointFeatures.values()));
        return pickupPoint;
    }

    @NotNull
    private PickupPointDto getPickupPointDto(Long id) {
        PickupPointDto pickupPointDto = new PickupPointDto();
        pickupPointDto.setId(id);
        pickupPointDto.setAddress("Moscow,Russia" + id);
        pickupPointDto.setDirections("Some directions" + id);
        pickupPointDto.setShelfLifeDays((byte) (5 / id));
        pickupPointDto.setPickupPointFeatures(Set.of(PickupPointFeatures.values()));
        return pickupPointDto;
    }
}
