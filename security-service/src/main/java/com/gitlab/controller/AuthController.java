package com.gitlab.controller;

import com.gitlab.controller.api.AuthRestApi;
import com.gitlab.dto.AuthDto;
import com.gitlab.dto.JwtDto;
import com.gitlab.dto.UserDto;
import com.gitlab.mapper.UserMapper;
import com.gitlab.service.AuthService;
import com.gitlab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthRestApi {

    private final UserService userService;
    private final AuthService authService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<?> create(UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper
                        .toDto(authService
                                .save(userMapper
                                        .toEntity(userDto))));
    }

    @Override
    public ResponseEntity<?> createToken(@RequestBody AuthDto authDto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword()));
        if (authenticate.isAuthenticated()) {
            return ResponseEntity.ok().body(authService.generateToken(authDto.getEmail()));
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    @Override
    public ResponseEntity<?> validationToken(@RequestBody JwtDto tokenDto) {
        authService.validateToken(tokenDto.getToken());
        return ResponseEntity.ok().body("Dfg");
    }

    @GetMapping("/auth/test")
    public void testing() {

    }
}
