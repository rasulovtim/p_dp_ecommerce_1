package com.gitlab.controller.api;

import com.gitlab.dto.PassportDto;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Passport REST")
@Tag(name = "Passport REST", description = "API passport description")
@RequestMapping("/api/passport")
public interface PassportRestApi {

    @GetMapping
    @ApiOperation(value = "Get all Passports")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Passports found"),
            @ApiResponse(code = 204, message = "Passports not present")}
    )
    ResponseEntity<List<PassportDto>> getAll();

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Passport by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Passport found"),
            @ApiResponse(code = 404, message = "Passport not found")}
    )
    ResponseEntity<PassportDto> get(@ApiParam(name = "id", value = "Passport.id") @PathVariable Long id);

    @PostMapping
    @ApiOperation(value = "Create Passport")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Passport created"),
            @ApiResponse(code = 400, message = "Passport not created")}
    )
    ResponseEntity<PassportDto> create(@ApiParam(name = "passport", value = "PassportDto") @Valid @RequestBody PassportDto passportDto);

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update Passport")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Passport updated"),
            @ApiResponse(code = 400, message = "Passport not updated")}
    )
    ResponseEntity<PassportDto> update(@ApiParam(name = "id", value = "Passport.id") @PathVariable Long id,
                                      @ApiParam(name = "passport", value = "PassportDto") @Valid @RequestBody PassportDto passportDto);

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Passport by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Passport deleted"),
            @ApiResponse(code = 404, message = "Passport not found")}
    )
    ResponseEntity<Void> delete(@ApiParam(name = "id", value = "Passport.id") @PathVariable Long id);


}
