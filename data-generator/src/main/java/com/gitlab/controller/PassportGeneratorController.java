package com.gitlab.controller;

import com.gitlab.controller.api.PassportGeneratorRestApi;
import com.gitlab.dto.PassportDto;
import com.gitlab.service.PassportGeneratorService;
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
public class PassportGeneratorController implements PassportGeneratorRestApi {

    private final PassportGeneratorService passportGeneratorService;

    @Override
    public ResponseEntity<List<PassportDto>> generatePassport(int count) {
        List<PassportDto> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(passportGeneratorService.generatePassport());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}