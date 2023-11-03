package com.gitlab.controller;


import com.gitlab.controllers.api.rest.UserRestApi;
import com.gitlab.dto.UserDto;
import com.gitlab.mapper.UserMapper;
import com.gitlab.model.User;
import com.gitlab.service.UserService;
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
public class UserRestController implements UserRestApi {

    private final UserMapper userMapper;

    private final UserService userService;

    @Override
    public ResponseEntity<List<UserDto>> getAll() {
        var users = userService.findAll();
        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok(users.stream().map(userMapper::toDto).toList());
        }
    }

    @Override
    public ResponseEntity<UserDto> get(Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<UserDto> create(UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper
                        .toDto(userService
                                .save(userMapper
                                        .toEntity(userDto))));
    }

    @Override
    public ResponseEntity<UserDto> update(Long id, UserDto userDto) {
        return userService.update(id, userMapper.toEntity(userDto))
                .map(example -> ResponseEntity.ok(userMapper.toDto(example)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        Optional<User> user = userService.delete(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }
}
