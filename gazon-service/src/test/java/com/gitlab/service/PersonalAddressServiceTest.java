package com.gitlab.service;

import com.gitlab.model.PersonalAddress;
import com.gitlab.repository.PersonalAddressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonalAddressServiceTest {

    @Mock
    private PersonalAddressRepository personalAddressRepository;
    @InjectMocks
    private PersonalAddressService personalAddressService;

    @Test
    void should_find_all_personalAddresses() {
        List<PersonalAddress> expectedResult = generatePersonalAddresses();
        when(personalAddressRepository.findAll()).thenReturn(generatePersonalAddresses());

        List<PersonalAddress> actualResult = personalAddressService.findAll();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_find_personalAddress_by_id() {
        long id = 1L;
        PersonalAddress expectedResult = generatePersonalAddress();
        when(personalAddressRepository.findById(id)).thenReturn(Optional.of(expectedResult));

        Optional<PersonalAddress> actualResult = personalAddressService.findById(id);

        assertEquals(expectedResult, actualResult.orElse(null));
    }

    @Test
    void should_save_personalAddress() {
        PersonalAddress expectedResult = generatePersonalAddress();
        when(personalAddressRepository.save(expectedResult)).thenReturn(expectedResult);

        PersonalAddress actualResult = personalAddressService.save(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_update_personalAddress() {
        long id = 1L;
        PersonalAddress personalAddressToUpdate = new PersonalAddress();
        personalAddressToUpdate.setId(id);
        personalAddressToUpdate.setAddress("modifiedText");
        personalAddressToUpdate.setDirections("modifiedText");


        PersonalAddress personalAddressBeforeUpdate = new PersonalAddress();
        personalAddressBeforeUpdate.setId(id);
        personalAddressBeforeUpdate.setAddress("unmodifiedText");
        personalAddressBeforeUpdate.setDirections("unmodifiedText");


        PersonalAddress updatedPersonalAddress = new PersonalAddress();
        updatedPersonalAddress.setId(id);
        updatedPersonalAddress.setAddress("modifiedText");
        updatedPersonalAddress.setDirections("modifiedText");


        when(personalAddressRepository.findById(id)).thenReturn(Optional.of(personalAddressBeforeUpdate));
        when(personalAddressRepository.save(updatedPersonalAddress)).thenReturn(updatedPersonalAddress);

        Optional<PersonalAddress> actualResult = personalAddressService.update(id, personalAddressToUpdate);

        assertEquals(updatedPersonalAddress, actualResult.orElse(null));
    }

    @Test
    void should_not_update_personalAddress_when_entity_not_found() {
        long id = 1L;
        PersonalAddress personalAddressToUpdate = generatePersonalAddress();

        when(personalAddressRepository.findById(id)).thenReturn(Optional.empty());

        Optional<PersonalAddress> actualResult = personalAddressService.update(id, personalAddressToUpdate);

        verify(personalAddressRepository, never()).save(any());
        assertNull(actualResult.orElse(null));
    }

    @Test
    void should_not_update_address_field_if_null() {
        long id = 1L;
        PersonalAddress personalAddressToUpdate = generatePersonalAddress();
        personalAddressToUpdate.setAddress(null);

        PersonalAddress personalAddressBeforeUpdate = generatePersonalAddress();

        when(personalAddressRepository.findById(id)).thenReturn(Optional.of(personalAddressBeforeUpdate));
        when(personalAddressRepository.save(personalAddressBeforeUpdate)).thenReturn(personalAddressBeforeUpdate);

        Optional<PersonalAddress> actualResult = personalAddressService.update(id, personalAddressToUpdate);

        verify(personalAddressRepository).save(personalAddressBeforeUpdate);
        assertNotNull(actualResult.orElse(personalAddressBeforeUpdate).getAddress());
    }

    @Test
    void should_not_update_apartment_field_if_null() {
        long id = 1L;
        PersonalAddress personalAddressToUpdate = generatePersonalAddress();
        personalAddressToUpdate.setApartment(null);

        PersonalAddress personalAddressBeforeUpdate = generatePersonalAddress();

        when(personalAddressRepository.findById(id)).thenReturn(Optional.of(personalAddressBeforeUpdate));
        when(personalAddressRepository.save(personalAddressBeforeUpdate)).thenReturn(personalAddressBeforeUpdate);

        Optional<PersonalAddress> actualResult = personalAddressService.update(id, personalAddressToUpdate);

        verify(personalAddressRepository).save(personalAddressBeforeUpdate);
        assertNotNull(actualResult.orElse(personalAddressBeforeUpdate).getApartment());
    }

    @Test
    void should_not_update_floor_field_if_null() {
        long id = 1L;
        PersonalAddress personalAddressToUpdate = generatePersonalAddress();
        personalAddressToUpdate.setFloor(null);

        PersonalAddress personalAddressBeforeUpdate = generatePersonalAddress();

        when(personalAddressRepository.findById(id)).thenReturn(Optional.of(personalAddressBeforeUpdate));
        when(personalAddressRepository.save(personalAddressBeforeUpdate)).thenReturn(personalAddressBeforeUpdate);

        Optional<PersonalAddress> actualResult = personalAddressService.update(id, personalAddressToUpdate);

        verify(personalAddressRepository).save(personalAddressBeforeUpdate);
        assertNotNull(actualResult.orElse(personalAddressBeforeUpdate).getFloor());
    }

    @Test
    void should_not_update_entrance_field_if_null() {
        long id = 1L;
        PersonalAddress personalAddressToUpdate = generatePersonalAddress();
        personalAddressToUpdate.setEntrance(null);

        PersonalAddress personalAddressBeforeUpdate = generatePersonalAddress();

        when(personalAddressRepository.findById(id)).thenReturn(Optional.of(personalAddressBeforeUpdate));
        when(personalAddressRepository.save(personalAddressBeforeUpdate)).thenReturn(personalAddressBeforeUpdate);

        Optional<PersonalAddress> actualResult = personalAddressService.update(id, personalAddressToUpdate);

        verify(personalAddressRepository).save(personalAddressBeforeUpdate);
        assertNotNull(actualResult.orElse(personalAddressBeforeUpdate).getEntrance());
    }

    @Test
    void should_not_update_directions_field_if_null() {
        long id = 1L;
        PersonalAddress personalAddressToUpdate = generatePersonalAddress();
        personalAddressToUpdate.setDirections(null);

        PersonalAddress personalAddressBeforeUpdate = generatePersonalAddress();

        when(personalAddressRepository.findById(id)).thenReturn(Optional.of(personalAddressBeforeUpdate));
        when(personalAddressRepository.save(personalAddressBeforeUpdate)).thenReturn(personalAddressBeforeUpdate);

        Optional<PersonalAddress> actualResult = personalAddressService.update(id, personalAddressToUpdate);

        verify(personalAddressRepository).save(personalAddressBeforeUpdate);
        assertNotNull(actualResult.orElse(personalAddressBeforeUpdate).getDirections());
    }

    @Test
    void should_not_update_doorCode_field_if_null() {
        long id = 1L;
        PersonalAddress personalAddressToUpdate = generatePersonalAddress();
        personalAddressToUpdate.setDoorCode(null);

        PersonalAddress personalAddressBeforeUpdate = generatePersonalAddress();

        when(personalAddressRepository.findById(id)).thenReturn(Optional.of(personalAddressBeforeUpdate));
        when(personalAddressRepository.save(personalAddressBeforeUpdate)).thenReturn(personalAddressBeforeUpdate);

        Optional<PersonalAddress> actualResult = personalAddressService.update(id, personalAddressToUpdate);

        verify(personalAddressRepository).save(personalAddressBeforeUpdate);
        assertNotNull(actualResult.orElse(personalAddressBeforeUpdate).getDoorCode());
    }

    @Test
    void should_not_update_postCode_field_if_null() {
        long id = 1L;
        PersonalAddress personalAddressToUpdate = generatePersonalAddress();
        personalAddressToUpdate.setPostCode(null);

        PersonalAddress personalAddressBeforeUpdate = generatePersonalAddress();

        when(personalAddressRepository.findById(id)).thenReturn(Optional.of(personalAddressBeforeUpdate));
        when(personalAddressRepository.save(personalAddressBeforeUpdate)).thenReturn(personalAddressBeforeUpdate);

        Optional<PersonalAddress> actualResult = personalAddressService.update(id, personalAddressToUpdate);

        verify(personalAddressRepository).save(personalAddressBeforeUpdate);
        assertNotNull(actualResult.orElse(personalAddressBeforeUpdate).getPostCode());
    }

    @Test
    void should_delete_personalAddress() {
        long id = 1L;
        when(personalAddressRepository.findById(id)).thenReturn(Optional.of(generatePersonalAddress()));

        personalAddressService.delete(id);

        verify(personalAddressRepository).deleteById(id);
    }

    @Test
    void should_not_delete_personalAddress_when_entity_not_found() {
        long id = 1L;
        when(personalAddressRepository.findById(id)).thenReturn(Optional.empty());

        personalAddressService.delete(id);

        verify(personalAddressRepository, never()).deleteById(anyLong());
    }

    private List<PersonalAddress> generatePersonalAddresses() {
        return List.of(
                generatePersonalAddress(1L),
                generatePersonalAddress(2L),
                generatePersonalAddress(3L),
                generatePersonalAddress(4L),
                generatePersonalAddress(5L)
        );
    }

    private PersonalAddress generatePersonalAddress(Long id) {
        PersonalAddress personalAddress = generatePersonalAddress();
        personalAddress.setId(id);
        return personalAddress;
    }

    private PersonalAddress generatePersonalAddress() {
        PersonalAddress personalAddress = new PersonalAddress();
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
}
