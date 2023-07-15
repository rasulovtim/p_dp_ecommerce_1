package com.gitlab.controller;

import com.gitlab.controller.api.PersonalAddressRestApi;
import com.gitlab.dto.PersonalAddressDto;
import com.gitlab.mapper.PersonalAddressMapper;
import com.gitlab.service.PersonalAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class PersonalAddressRestController implements PersonalAddressRestApi {

    private final PersonalAddressService personalAddressService;
    private final PersonalAddressMapper personalAddressMapper;

    @Override
    public ResponseEntity<List<PersonalAddressDto>> getAll() {
        var personalAddresses = personalAddressService.findAll();
        return personalAddresses.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(personalAddresses.stream().map(personalAddressMapper::toDto).toList());
    }

    @Override
    public ResponseEntity<PersonalAddressDto> get(Long id) {
        return personalAddressService.findById(id)
                .map(value -> ResponseEntity.ok(personalAddressMapper.toDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<PersonalAddressDto> create(PersonalAddressDto personalAddressDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(personalAddressMapper
                        .toDto(personalAddressService
                                .save(personalAddressMapper
                                        .toEntity(personalAddressDto))));
    }

    @Override
    public ResponseEntity<PersonalAddressDto> update(Long id, PersonalAddressDto personalAddressDto) {
        return personalAddressService.update(id, personalAddressMapper.toEntity(personalAddressDto))
                .map(value -> ResponseEntity.ok(personalAddressMapper.toDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        return personalAddressService.delete(id).isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok().build();
    }
}