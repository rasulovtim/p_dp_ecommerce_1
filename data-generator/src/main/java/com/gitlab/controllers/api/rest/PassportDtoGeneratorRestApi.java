package com.gitlab.controllers.api.rest;

import com.gitlab.dto.PassportDto;
import com.gitlab.dto.UserDto;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api(tags = "PassportDto Generator REST")
@Tag(name = "PassportDto Generator REST", description = "API PassportDto Generator description")
public interface PassportDtoGeneratorRestApi {
    @GetMapping("generate/passport")
    @ApiOperation(value = "Generate PassportDto")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "PassportDto generated"),
            @ApiResponse(code = 400, message = "PassportDto not generated")}
    )
    ResponseEntity<List<PassportDto>> generateUserDto(@ApiParam(name = "count", value = "The count of PassportDto to create")
                                                  @RequestParam(name = "count") int count);
}
