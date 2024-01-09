package com.gitlab.controllers.api.rest;

import com.gitlab.dto.PassportDto;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Api(tags = "Passport REST")
@Tag(name = "Passport REST", description = "API passport description" )
public interface PassportRestApi {

    @GetMapping("/api/passport")
    @ApiOperation(value = "Get all Passport")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Passport found"),
            @ApiResponse(code = 204, message = "Passport not present")}
    )
    ResponseEntity<List<PassportDto>> getPage(@ApiParam(name = "page") @RequestParam(required = false, value = "page") Integer page,
                                              @ApiParam(name = "size") @RequestParam(required = false, value = "size") Integer size);

    @GetMapping("/api/passport/{id}")
    @ApiOperation(value = "Get Passport by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Passport found"),
            @ApiResponse(code = 404, message = "Passport not found")}
    )
    ResponseEntity<PassportDto> get(@ApiParam(name = "id", value = "Passport.id") @PathVariable(value = "id") Long id);

    @PatchMapping("/api/passport/{id}")
    @ApiOperation(value = "Update Passport")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Passport updated"),
            @ApiResponse(code = 404, message = "Passport not updated")}
    )
    ResponseEntity<PassportDto> update(@ApiParam(name = "id", value = "Passport.id") @PathVariable(value = "id") Long id,
                                   @ApiParam(name = "passport", value = "PassportDto") @Valid @RequestBody PassportDto passportDto);

    @PostMapping("/api/passport")
    @ApiOperation(value = "Create Passport")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Passport created"),
            @ApiResponse(code = 400, message = "Passport not created")}
    )
    ResponseEntity<PassportDto> create(@ApiParam(name = "passport", value = "PassportDto") @Valid @RequestBody PassportDto passportDto);

    @DeleteMapping("/api/passport/{id}")
    @ApiOperation(value = "Delete Passport by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Passport deleted"),
            @ApiResponse(code = 404, message = "Passport not found")}
    )
    ResponseEntity<Void> delete(@ApiParam(name = "id", value = "Passport.id") @PathVariable(value = "id") Long id);
}
