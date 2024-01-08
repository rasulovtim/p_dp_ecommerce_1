package com.gitlab.controllers.api.rest;

import com.gitlab.dto.RoleDto;
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

@Api(tags = "Role REST")
@Tag(name = "Role REST", description = "API roles description")
public interface RoleRestApi {

    @GetMapping("/api/role")
    @ApiOperation(value = "Get all Role")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Roles found"),
            @ApiResponse(code = 404, message = "Roles not present")}
    )
    ResponseEntity<List<RoleDto>> getAll();

    @GetMapping("/api/role/{id}")
    @ApiOperation(value = "Get Role by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Role found"),
            @ApiResponse(code = 404, message = "Role not found")}
    )
    ResponseEntity<RoleDto> get(@ApiParam(name = "id", value = "Role.id") @PathVariable(value = "id") Long id);

    @PatchMapping("/api/role/{id}")
    @ApiOperation(value = "Update Role")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Role updated"),
            @ApiResponse(code = 400, message = "Role not updated")}
    )
    ResponseEntity<RoleDto> update(@ApiParam(name = "id", value = "Role.id") @PathVariable(value = "id") Long id,
                                   @ApiParam(name = "role", value = "RoleDto") @Valid @RequestBody RoleDto roleDto);

    @PostMapping("/api/role")
    @ApiOperation(value = "Create Role")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Role created"),
            @ApiResponse(code = 400, message = "Role not created")}
    )
    ResponseEntity<RoleDto> create(@ApiParam(name = "role", value = "RoleDto") @Valid @RequestBody RoleDto roleDto);

    @DeleteMapping("/api/role/{id}")
    @ApiOperation(value = "Delete Role by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Role deleted"),
            @ApiResponse(code = 404, message = "Role not found")}
    )
    ResponseEntity<Void> delete(@ApiParam(name = "id", value = "Role.id") @PathVariable(value = "id") Long id);
}

