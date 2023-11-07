package com.gitlab.controller;

import com.gitlab.controllers.api.rest.UserDtoGeneratorRestApi;
import com.gitlab.dto.UserDto;
import com.gitlab.service.UserDtoGeneratorService;
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
public class UserDtoGeneratorController implements UserDtoGeneratorRestApi {

    private final UserDtoGeneratorService userDtoGeneratorService;

    @Override
    public ResponseEntity<List<UserDto>> generateUserDto(int count) {
        List<UserDto> result = new ArrayList<>();

        for (int i = 0; i < count; i++){
            result.add(userDtoGeneratorService.generateUserDto());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
