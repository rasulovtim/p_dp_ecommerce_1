package com.gitlab.service;

import com.gitlab.model.PickupPoint;
import com.gitlab.repository.PickupPointRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PickupPointServiceTest {

    @Mock
    private PickupPointRepository pickupPointRepository;
    @InjectMocks
    private PickupPointService pickupPointService;

    @Test
    void should_find_all_pickupPoints() {
        List<PickupPoint> expectedResult = generatePickupPoints();
        when(pickupPointRepository.findAll()).thenReturn(generatePickupPoints());

        List<PickupPoint> actualResult = pickupPointService.findAll();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_find_pickupPoint_by_id() {
        long id = 1L;
        PickupPoint expectedResult = generatePickupPoint();
        when(pickupPointRepository.findById(id)).thenReturn(Optional.of(expectedResult));

        Optional<PickupPoint> actualResult = pickupPointService.findById(id);

        assertEquals(expectedResult, actualResult.orElse(null));
    }

    @Test
    void should_save_pickupPoint() {
        PickupPoint expectedResult = generatePickupPoint();
        when(pickupPointRepository.save(expectedResult)).thenReturn(expectedResult);

        PickupPoint actualResult = pickupPointService.save(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_update_pickupPoint() {
        long id = 1L;
        PickupPoint pickupPointToUpdate = new PickupPoint();
        pickupPointToUpdate.setId(id);
        pickupPointToUpdate.setAddress("modifiedText");
        pickupPointToUpdate.setDirections("modifiedText");
        pickupPointToUpdate.setShelfLifeDays((byte) 2);

        PickupPoint pickupPointBeforeUpdate = new PickupPoint();
        pickupPointBeforeUpdate.setId(id);
        pickupPointBeforeUpdate.setAddress("unmodifiedText");
        pickupPointBeforeUpdate.setDirections("unmodifiedText");
        pickupPointBeforeUpdate.setShelfLifeDays((byte) 1);

        PickupPoint updatedPickupPoint = new PickupPoint();
        updatedPickupPoint.setId(id);
        updatedPickupPoint.setAddress("modifiedText");
        updatedPickupPoint.setDirections("modifiedText");
        updatedPickupPoint.setShelfLifeDays((byte) 2);

        when(pickupPointRepository.findById(id)).thenReturn(Optional.of(pickupPointBeforeUpdate));
        when(pickupPointRepository.save(updatedPickupPoint)).thenReturn(updatedPickupPoint);

        Optional<PickupPoint> actualResult = pickupPointService.update(id, pickupPointToUpdate);

        assertEquals(updatedPickupPoint, actualResult.orElse(null));
    }

    @Test
    void should_not_update_pickupPoint_when_entity_not_found() {
        long id = 1L;
        PickupPoint pickupPointToUpdate = generatePickupPoint();

        when(pickupPointRepository.findById(id)).thenReturn(Optional.empty());

        Optional<PickupPoint> actualResult = pickupPointService.update(id, pickupPointToUpdate);

        verify(pickupPointRepository, never()).save(any());
        assertNull(actualResult.orElse(null));
    }

    @Test
    void should_not_update_address_field_if_null() {
        long id = 1L;
        PickupPoint pickupPointToUpdate = generatePickupPoint();
        pickupPointToUpdate.setAddress(null);

        PickupPoint pickupPointBeforeUpdate = generatePickupPoint();

        when(pickupPointRepository.findById(id)).thenReturn(Optional.of(pickupPointBeforeUpdate));
        when(pickupPointRepository.save(pickupPointBeforeUpdate)).thenReturn(pickupPointBeforeUpdate);

        Optional<PickupPoint> actualResult = pickupPointService.update(id, pickupPointToUpdate);

        verify(pickupPointRepository).save(pickupPointBeforeUpdate);
        assertNotNull(actualResult.orElse(pickupPointBeforeUpdate).getAddress());
    }

    @Test
    void should_not_update_shelfLifeDays_field_if_null() {
        long id = 1L;
        PickupPoint pickupPointToUpdate = generatePickupPoint();
        pickupPointToUpdate.setShelfLifeDays(null);

        PickupPoint pickupPointBeforeUpdate = generatePickupPoint();

        when(pickupPointRepository.findById(id)).thenReturn(Optional.of(pickupPointBeforeUpdate));
        when(pickupPointRepository.save(pickupPointBeforeUpdate)).thenReturn(pickupPointBeforeUpdate);

        Optional<PickupPoint> actualResult = pickupPointService.update(id, pickupPointToUpdate);

        verify(pickupPointRepository).save(pickupPointBeforeUpdate);
        assertNotNull(actualResult.orElse(pickupPointBeforeUpdate).getShelfLifeDays());
    }

    @Test
    void should_not_update_directions_field_if_null() {
        long id = 1L;
        PickupPoint pickupPointToUpdate = generatePickupPoint();
        pickupPointToUpdate.setDirections(null);

        PickupPoint pickupPointBeforeUpdate = generatePickupPoint();

        when(pickupPointRepository.findById(id)).thenReturn(Optional.of(pickupPointBeforeUpdate));
        when(pickupPointRepository.save(pickupPointBeforeUpdate)).thenReturn(pickupPointBeforeUpdate);

        Optional<PickupPoint> actualResult = pickupPointService.update(id, pickupPointToUpdate);

        verify(pickupPointRepository).save(pickupPointBeforeUpdate);
        assertNotNull(actualResult.orElse(pickupPointBeforeUpdate).getDirections());
    }

    @Test
    void should_not_update_pickupPointFeatures_field_if_null() {
        long id = 1L;
        PickupPoint pickupPointToUpdate = generatePickupPoint();
        pickupPointToUpdate.setPickupPointFeatures(null);

        PickupPoint pickupPointBeforeUpdate = generatePickupPoint();

        when(pickupPointRepository.findById(id)).thenReturn(Optional.of(pickupPointBeforeUpdate));
        when(pickupPointRepository.save(pickupPointBeforeUpdate)).thenReturn(pickupPointBeforeUpdate);

        Optional<PickupPoint> actualResult = pickupPointService.update(id, pickupPointToUpdate);

        verify(pickupPointRepository).save(pickupPointBeforeUpdate);
        assertNotNull(actualResult.orElse(pickupPointBeforeUpdate).getPickupPointFeatures());
    }

    @Test
    void should_delete_pickupPoint() {
        long id = 1L;
        when(pickupPointRepository.findById(id)).thenReturn(Optional.of(generatePickupPoint()));

        pickupPointService.delete(id);

        verify(pickupPointRepository).deleteById(id);
    }

    @Test
    void should_not_delete_pickupPoint_when_entity_not_found() {
        long id = 1L;
        when(pickupPointRepository.findById(id)).thenReturn(Optional.empty());

        pickupPointService.delete(id);

        verify(pickupPointRepository, never()).deleteById(anyLong());
    }

    private List<PickupPoint> generatePickupPoints() {
        return List.of(
                generatePickupPoint(1L),
                generatePickupPoint(2L),
                generatePickupPoint(3L),
                generatePickupPoint(4L),
                generatePickupPoint(5L)
        );
    }

    private PickupPoint generatePickupPoint(Long id) {
        PickupPoint pickupPoint = generatePickupPoint();
        pickupPoint.setId(id);
        return pickupPoint;
    }

    private PickupPoint generatePickupPoint() {
        PickupPoint pickupPoint = new PickupPoint();
        pickupPoint.setId(1L);
        pickupPoint.setAddress("Test Address");
        pickupPoint.setDirections("Test Directions");
        pickupPoint.setShelfLifeDays((byte) 5);
        pickupPoint.setPickupPointFeatures(Set.of(PickupPoint.PickupPointFeatures.values()));
        return pickupPoint;
    }
}
