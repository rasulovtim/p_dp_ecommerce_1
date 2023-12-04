package com.gitlab.controller.api;

import com.gitlab.dto.PassportDto;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api(tags = "Passport Generator REST")
@Tag(name = "Passport Generator REST", description = "API Passport Generator description")
public interface PassportGeneratorRestApi {
    @GetMapping("generate/passport")
    @ApiOperation(value = "Generate Passport")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Passport generated"),
            @ApiResponse(code = 400, message = "Passport not generated")}
    )
    ResponseEntity<List<PassportDto>> generatePassport(@ApiParam(name = "count", value = "The count of Passport to create")
                                                       @RequestParam(name = "count") int count);
}