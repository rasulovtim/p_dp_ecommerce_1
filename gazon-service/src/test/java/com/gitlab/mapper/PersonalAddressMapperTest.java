package com.gitlab.mapper;

import com.gitlab.dto.PersonalAddressDto;
import com.gitlab.model.PersonalAddress;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PersonalAddressMapperTest {

    private final PersonalAddressMapper mapper = Mappers.getMapper(PersonalAddressMapper.class);

    @Test
    void should_should_map_PersonalAddress_to_Dto() {
        PersonalAddress personalAddress = getPersonalAddress(1L);

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
        PersonalAddressDto personalAddressDto = getPersonalAddressDto(1L);

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

    @Test
    void should_map_personalAddressList_to_DtoList() {
        List<PersonalAddress> personalAddressList = List.of(getPersonalAddress(1L), getPersonalAddress(2L), getPersonalAddress(3L));

        List<PersonalAddressDto> personalAddressDtoList = mapper.toDtoList(personalAddressList);

        assertNotNull(personalAddressDtoList);
        assertEquals(personalAddressList.size(), personalAddressList.size());
        for (int i = 0; i < personalAddressDtoList.size(); i++) {
            PersonalAddressDto dto = personalAddressDtoList.get(i);
            PersonalAddress entity = personalAddressList.get(i);
            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getAddress(), entity.getAddress());
            assertEquals(dto.getApartment(), entity.getApartment());
            assertEquals(dto.getDirections(), entity.getDirections());
            assertEquals(dto.getEntrance(), entity.getEntrance());
            assertEquals(dto.getFloor(), entity.getFloor());
            assertEquals(dto.getPostCode(), entity.getPostCode());
            assertEquals(dto.getDoorCode(), entity.getDoorCode());
        }
    }

    @Test
    void should_map_personalAddressDtoList_to_EntityList() {
        List<PersonalAddressDto> personalAddressDtoList = List.of(getPersonalAddressDto(1L), getPersonalAddressDto(2L), getPersonalAddressDto(3L));

        List<PersonalAddress> personalAddressList = mapper.toEntityList(personalAddressDtoList);

        assertNotNull(personalAddressList);
        assertEquals(personalAddressList.size(), personalAddressList.size());
        for (int i = 0; i < personalAddressList.size(); i++) {
            PersonalAddressDto dto = personalAddressDtoList.get(i);
            PersonalAddress entity = personalAddressList.get(i);
            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getAddress(), entity.getAddress());
            assertEquals(dto.getApartment(), entity.getApartment());
            assertEquals(dto.getDirections(), entity.getDirections());
            assertEquals(dto.getEntrance(), entity.getEntrance());
            assertEquals(dto.getFloor(), entity.getFloor());
            assertEquals(dto.getPostCode(), entity.getPostCode());
            assertEquals(dto.getDoorCode(), entity.getDoorCode());
        }
    }

    @NotNull
    private PersonalAddress getPersonalAddress(Long id) {
        PersonalAddress personalAddress = new PersonalAddress();
        personalAddress.setId(id);
        personalAddress.setAddress("Moscow,Russia" + id);
        personalAddress.setApartment("123" + id);
        personalAddress.setDirections("Some directions" + id);
        personalAddress.setEntrance("4" + id);
        personalAddress.setFloor("10" + id);
        personalAddress.setPostCode("123456" + id);
        personalAddress.setDoorCode("1111" + id);
        return personalAddress;
    }

    @NotNull
    private PersonalAddressDto getPersonalAddressDto(Long id) {
        PersonalAddressDto personalAddressDto = new PersonalAddressDto();
        personalAddressDto.setId(id);
        personalAddressDto.setAddress("Moscow,Russia" + id);
        personalAddressDto.setApartment("123" + id);
        personalAddressDto.setDirections("Some directions" + id);
        personalAddressDto.setEntrance("4" + id);
        personalAddressDto.setFloor("1" + id);
        personalAddressDto.setPostCode("12345" + id);
        personalAddressDto.setDoorCode("111" + id);
        return personalAddressDto;
    }
}
