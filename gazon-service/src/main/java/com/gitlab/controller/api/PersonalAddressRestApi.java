package com.gitlab.controller.api;

import com.gitlab.dto.PersonalAddressDto;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Personal Address REST")
@Tag(name = "Personal Address REST", description = "API for personal address")
@RequestMapping("/api/personal_address")
public interface PersonalAddressRestApi {
    @GetMapping
    @ApiOperation(value = "Get all Personal Addresses")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Personal Addresses found"),
            @ApiResponse(code = 204, message = "Personal Addresses not present")}
    )
    ResponseEntity<List<PersonalAddressDto>> getAll();

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Personal Address by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Personal Address found"),
            @ApiResponse(code = 404, message = "Personal Address not found")}
    )
    ResponseEntity<PersonalAddressDto> get(@ApiParam(name = "id", value = "PersonalAddress.id")
                                           @PathVariable Long id);

    @PostMapping
    @ApiOperation(value = "Create Personal Address")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Personal Address created"),
            @ApiResponse(code = 400, message = "Personal Address not created")}
    )
    ResponseEntity<PersonalAddressDto> create(@ApiParam(name = "personalAddress", value = "PersonalAddressDto")
                                              @Valid @RequestBody PersonalAddressDto personalAddressDto);

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update Personal Address")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Personal Address updated"),
            @ApiResponse(code = 400, message = "Personal Address not updated")}
    )
    ResponseEntity<PersonalAddressDto> update(@ApiParam(name = "id", value = "PersonalAddress.id")
                                              @PathVariable Long id,
                                              @ApiParam(name = "personalAddress", value = "PersonalAddressDto")
                                              @Valid @RequestBody PersonalAddressDto personalAddressDto);

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Personal Address by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Personal Address deleted"),
            @ApiResponse(code = 404, message = "Personal Address not found")}
    )
    ResponseEntity<Void> delete(@ApiParam(name = "id", value = "PersonalAddress.id")
                                @PathVariable Long id);
}
