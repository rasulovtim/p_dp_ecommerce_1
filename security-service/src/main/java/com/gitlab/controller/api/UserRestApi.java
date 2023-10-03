package com.gitlab.controller.api;

import com.gitlab.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "User REST")
@Tag(name = "User REST", description = "API user description")
public interface UserRestApi {

    @GetMapping("/api/user")
    @ApiOperation(value = "Get all Users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Users found"),
            @ApiResponse(code = 204, message = "Users not present")}
    )
    ResponseEntity<List<UserDto>> getAll();

    @GetMapping("/api/user/{id}")
    @ApiOperation(value = "Get User by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User found"),
            @ApiResponse(code = 404, message = "User not found")}
    )
    ResponseEntity<UserDto> get(@ApiParam(name = "id", value = "User.id") @PathVariable(value = "id") Long id);

    @PostMapping("/api/user")
    @ApiOperation(value = "Create User")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User created"),
            @ApiResponse(code = 400, message = "User not created")}
    )
    ResponseEntity<UserDto> create(@ApiParam(name = "user", value = "UserDto") @Valid @RequestBody UserDto userDto);

    @PatchMapping("/api/user/{id}")
    @ApiOperation(value = "Update User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User updated"),
            @ApiResponse(code = 400, message = "User not updated")}
    )
    ResponseEntity<UserDto> update(@ApiParam(name = "id", value = "User.id") @PathVariable(value = "id") Long id,
                                      @ApiParam(name = "user", value = "UserDto") @Valid @RequestBody UserDto userDto);

    @DeleteMapping("/api/user/{id}")
    @ApiOperation(value = "Delete User by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User deleted"),
            @ApiResponse(code = 404, message = "User not found")}
    )
    ResponseEntity<Void> delete(@ApiParam(name = "id", value = "User.id") @PathVariable(value = "id") Long id);
}
