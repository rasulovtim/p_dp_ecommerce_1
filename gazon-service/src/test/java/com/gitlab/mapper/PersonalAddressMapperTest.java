package com.gitlab.mapper;

import com.gitlab.dto.PersonalAddressDto;
import com.gitlab.model.PersonalAddress;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PersonalAddressMapperTest {

    private final PersonalAddressMapper mapper = Mappers.getMapper(PersonalAddressMapper.class);

    @Test
    void should_should_map_PersonalAddress_to_Dto() {
        PersonalAddress personalAddress = new PersonalAddress();
        personalAddress.setId(1L);
        personalAddress.setAddress("Moscow,Russia");
        personalAddress.setApartment("123");
        personalAddress.setDirections("Some directions");
        personalAddress.setEntrance("4");
        personalAddress.setFloor("10");
        personalAddress.setPostCode("123456");
        personalAddress.setDoorCode("1111");

        PersonalAddressDto actualResult = mapper.toDto(personalAddress);

        assertNotNull(actualResult);
        assertEquals(personalAddress.getId(), actualResult.getId());
        assertEquals(personalAddress.getAddress(), actualResult.getAddress());
        assertEquals(personalAddress.getApartment(), actualResult.getApartment());
        assertEquals(personalAddress.getDirections(), actualResult.getDirections());
        assertEquals(personalAddress.getEntrance(), actualResult.getEntrance());
        assertEquals(personalAddress.getFloor(), actualResult.getFloor());
        assertEquals(personalAddress.getPostCode(), actualResult.getPostCode());
        assertEquals(personalAddress.getDoorCode(), actualResult.getDoorCode());


    }

    @Test
    void should_should_map_PersonalAddressDto_to_Entity() {
        PersonalAddressDto personalAddressDto = new PersonalAddressDto();
        personalAddressDto.setId(1L);
        personalAddressDto.setAddress("Moscow,Russia");
        personalAddressDto.setApartment("123");
        personalAddressDto.setDirections("Some directions");
        personalAddressDto.setEntrance("4");
        personalAddressDto.setFloor("10");
        personalAddressDto.setPostCode("123456");
        personalAddressDto.setDoorCode("1111");

        PersonalAddress actualResult = mapper.toEntity(personalAddressDto);

        assertNotNull(actualResult);
        assertEquals(personalAddressDto.getId(), actualResult.getId());
        assertEquals(personalAddressDto.getAddress(), actualResult.getAddress());
        assertEquals(personalAddressDto.getApartment(), actualResult.getApartment());
        assertEquals(personalAddressDto.getDirections(), actualResult.getDirections());
        assertEquals(personalAddressDto.getEntrance(), actualResult.getEntrance());
        assertEquals(personalAddressDto.getFloor(), actualResult.getFloor());
        assertEquals(personalAddressDto.getPostCode(), actualResult.getPostCode());
        assertEquals(personalAddressDto.getDoorCode(), actualResult.getDoorCode());
    }
}
