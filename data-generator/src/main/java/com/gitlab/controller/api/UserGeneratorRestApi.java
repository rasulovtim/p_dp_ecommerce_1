package com.gitlab.controller.api;

import com.gitlab.dto.UserDto;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api(tags = "User Generator REST")
@Tag(name = "User Generator REST", description = "API User Generator description")
public interface UserGeneratorRestApi {

    @GetMapping("generate/user")
    @ApiOperation(value = "Generate User")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User generated"),
            @ApiResponse(code = 400, message = "User not generated")}
    )
    ResponseEntity<List<UserDto>> generateUser(@ApiParam(name = "count", value = "The count of User to create")
                                               @RequestParam(name = "count") int count);
}