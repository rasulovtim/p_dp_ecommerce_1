package com.gitlab.service;

import com.gitlab.dto.PersonalAddressDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonalAddressGeneratorService {

    public PersonalAddressDto generatePersonalAddressDto(String additionalData) {
        PersonalAddressDto personalAddressDto = new PersonalAddressDto();

        personalAddressDto.setAddress("address" + additionalData);
        personalAddressDto.setDirections("direction" + additionalData);
        personalAddressDto.setApartment("apartment" + additionalData);
        personalAddressDto.setFloor("floor" + additionalData);
        personalAddressDto.setEntrance("entance" + additionalData);
        personalAddressDto.setDoorCode("doorode" + additionalData);
        personalAddressDto.setPostCode("postode" + additionalData);

        return personalAddressDto;
    }
}