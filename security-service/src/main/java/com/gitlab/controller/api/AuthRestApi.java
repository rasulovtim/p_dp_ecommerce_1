package com.gitlab.controller.api;

import com.gitlab.dto.AuthRequest;
import com.gitlab.dto.JwtDto;
import com.gitlab.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(tags = "Authentication REST")
@Tag(name = "Authentication REST", description = "API authentication description")
public interface AuthRestApi {

    @PostMapping("/api/auth/register")
    @ApiOperation(value = "Register User")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User registered"),
            @ApiResponse(code = 400, message = "User not registered")}
    )
    ResponseEntity<?> create(@ApiParam(name = "user", value = "UserDto") @Valid @RequestBody UserDto userDto);

    @PostMapping("/api/auth/token")
    @ApiOperation(value = "Generate Token")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Token generated"),
            @ApiResponse(code = 400, message = "Token not generated")}
    )
    ResponseEntity<?> createToken(@ApiParam(name = "token", value = "JwtDto") @RequestBody AuthRequest authRequest);

    @GetMapping("/api/auth/validate")
    @ApiOperation(value = "Validate Token")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Token is valid"),
            @ApiResponse(code = 400, message = "Token not valid")}
    )
    ResponseEntity<?> validationToken(@ApiParam(name = "token", value = "JwtDto") @RequestBody JwtDto token);
}
