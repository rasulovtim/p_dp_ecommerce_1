package com.gitlab.controller;

import com.gitlab.controllers.api.rest.PassportDtoGeneratorRestApi;
import com.gitlab.dto.PassportDto;
import com.gitlab.service.PassportDtoGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class PassportDtoGeneratorController implements PassportDtoGeneratorRestApi {

    private final PassportDtoGeneratorService passportDtoGeneratorService;

    @Override
    public ResponseEntity<List<PassportDto>> generateUserDto(int count) {
        List<PassportDto> result = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            result.add(passportDtoGeneratorService.generatePassportDto());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
