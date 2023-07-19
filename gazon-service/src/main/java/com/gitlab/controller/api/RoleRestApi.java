package com.gitlab.controller.api;

import com.gitlab.model.Role;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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


}
