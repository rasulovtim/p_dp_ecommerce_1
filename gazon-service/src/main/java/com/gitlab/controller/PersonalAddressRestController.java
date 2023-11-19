package com.gitlab.controller;

import com.gitlab.controllers.api.rest.PersonalAddressRestApi;
import com.gitlab.dto.PersonalAddressDto;
import com.gitlab.service.PersonalAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class PersonalAddressRestController implements PersonalAddressRestApi {

    private final PersonalAddressService personalAddressService;

    @Override
    public ResponseEntity<List<PersonalAddressDto>> getAll() {
        List<PersonalAddressDto> personalAddressDtos = personalAddressService.findAllDto();
        return personalAddressDtos.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(personalAddressDtos);
    }

    @Override
    public ResponseEntity<PersonalAddressDto> get(Long id) {
        return personalAddressService.findByIdDto(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<PersonalAddressDto> create(PersonalAddressDto personalAddressDto) {
        PersonalAddressDto createdPersonalAddressDto = personalAddressService.saveDto(personalAddressDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPersonalAddressDto);
    }

    @Override
    public ResponseEntity<PersonalAddressDto> update(Long id, PersonalAddressDto personalAddressDto) {
        Optional<PersonalAddressDto> updatedPersonalAddressDto = personalAddressService.updateDto(id, personalAddressDto);

        if (updatedPersonalAddressDto.isPresent()) {
            return ResponseEntity.ok(updatedPersonalAddressDto.orElse(null));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        Optional<PersonalAddressDto> deletedPersonalAddressDto = personalAddressService.deleteDto(id);

        if (deletedPersonalAddressDto.isPresent()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}