package com.gitlab.controller;

import com.gitlab.controller.api.AuthRestApi;
import com.gitlab.dto.AuthRequest;
import com.gitlab.dto.JwtDto;
import com.gitlab.dto.MessageResponse;
import com.gitlab.dto.UserDto;
import com.gitlab.mapper.UserMapper;
import com.gitlab.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthRestApi {

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
    public ResponseEntity<?> createToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return ResponseEntity.ok().body(new JwtDto(authService.generateToken(authRequest.getEmail())));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid access"));
        }
    }

    @Override
    public ResponseEntity<?> validationToken(@RequestBody JwtDto tokenDto) {
        authService.validateToken(tokenDto.getToken());
        return ResponseEntity.ok().body(new MessageResponse("Validation success"));
    }
}
