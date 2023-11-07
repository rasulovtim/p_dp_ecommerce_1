package com.gitlab.controllers.api.rest;

import com.gitlab.dto.UserDto;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api(tags = "UserDto Generator REST")
@Tag(name = "UserDto Generator REST", description = "API UserDto Generator description")
public interface UserDtoGeneratorRestApi {

    @GetMapping("generate/user")
    @ApiOperation(value = "Generate UserDto")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "UserDto generated"),
            @ApiResponse(code = 400, message = "UserDto not generated")}
    )
    ResponseEntity<List<UserDto>> generateUserDto(@ApiParam(name = "count", value = "The count of UserDto to create")
                                            @RequestParam int count);
}
