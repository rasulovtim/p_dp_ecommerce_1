package com.gitlab.controller;

import com.gitlab.controller.api.UserGeneratorRestApi;
import com.gitlab.dto.UserDto;
import com.gitlab.service.UserGeneratorService;
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
public class UserGeneratorController implements UserGeneratorRestApi {

    private final UserGeneratorService userGeneratorService;

    @Override
    public ResponseEntity<List<UserDto>> generateUser(int count) {
        List<UserDto> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(userGeneratorService.generateUser());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}