package com.gitlab.controller;

import com.gitlab.controller.api.PassportRestApi;
import com.gitlab.dto.PassportDto;
import com.gitlab.mapper.PassportMapper;
import com.gitlab.model.Passport;
import com.gitlab.service.PassportService;
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
public class PassportRestController implements PassportRestApi {

    private final PassportService passportService;
    private final PassportMapper passportMapper;

    @Override
    public ResponseEntity<List<PassportDto>> getAll() {
        var passports = passportService.findAll();
        if (passports.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(passports.stream().map(passportMapper::toDto).toList());
        }
    }

    @Override
    public ResponseEntity<PassportDto> get(Long id) {
        return passportService.findById(id)
                .map(passport -> ResponseEntity.ok(passportMapper.toDto(passport)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<PassportDto> create(PassportDto passportDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(passportMapper
                        .toDto(passportService
                                .save(passportMapper
                                        .toEntity(passportDto))));
    }

    @Override
    public ResponseEntity<PassportDto> update(Long id, PassportDto passportDto) {
        return passportService.update(id, passportMapper.toEntity(passportDto))
                .map(passport -> ResponseEntity.ok(passportMapper.toDto(passport)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        Optional<Passport> passport = passportService.delete(id);
        if (passport.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }
}