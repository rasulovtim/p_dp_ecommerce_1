package com.gitlab.controller.api;

import com.gitlab.dto.ExampleDto;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Example REST")
@Tag(name = "Example REST", description = "API example description")
@RequestMapping("/api/example")
public interface ExampleRestApi {

    @GetMapping
    @ApiOperation(value = "Get all Examples")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Examples found"),
            @ApiResponse(code = 204, message = "Examples not present")}
    )
    ResponseEntity<List<ExampleDto>> getAll();

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Example by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Example found"),
            @ApiResponse(code = 404, message = "Example not found")}
    )
    ResponseEntity<ExampleDto> get(@ApiParam(name = "id", value = "Example.id") @PathVariable Long id);

    @PostMapping
    @ApiOperation(value = "Create Example")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Example created"),
            @ApiResponse(code = 400, message = "Example not created")}
    )
    ResponseEntity<ExampleDto> create(@ApiParam(name = "example", value = "ExampleDto") @Valid @RequestBody ExampleDto exampleDto);

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update Example")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Example updated"),
            @ApiResponse(code = 400, message = "Example not updated")}
    )
    ResponseEntity<ExampleDto> update(@ApiParam(name = "id", value = "Example.id") @PathVariable Long id,
                                      @ApiParam(name = "example", value = "ExampleDto") @Valid @RequestBody ExampleDto exampleDto);

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Example by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Example deleted"),
            @ApiResponse(code = 404, message = "Example not found")}
    )
    ResponseEntity<Void> delete(@ApiParam(name = "id", value = "Example.id") @PathVariable Long id);
}
