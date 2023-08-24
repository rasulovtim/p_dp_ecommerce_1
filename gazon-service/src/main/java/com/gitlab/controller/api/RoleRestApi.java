package com.gitlab.controller.api;

import com.gitlab.model.Role;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Api(tags = "Role REST")
@Tag(name = "Role REST", description = "API roles description")
@RequestMapping("/api/role")
public interface RoleRestApi {

    @GetMapping
    @ApiOperation(value = "Get all Role")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Roles found"),
            @ApiResponse(code = 204, message = "Roles not present")}
    )
    ResponseEntity<List<Role>> getAll();

    @GetMapping("/{name}")
    @ApiOperation(value = "Get Name by role")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User found"),
            @ApiResponse(code = 404, message = "User not found")}
    )
    ResponseEntity<Role> get(@ApiParam(name = "name", value = "Role.name") @PathVariable String name);

}
