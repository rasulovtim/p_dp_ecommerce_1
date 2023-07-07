package com.gitlab.controller.api;

import com.gitlab.dto.PostomatDto;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Postomat REST")
@Tag(name = "Postomat REST", description = "API for postomat")
@RequestMapping("/api/postomat")
public interface PostomatRestApi {

    @GetMapping
    @ApiOperation(value = "Get all Postomats")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Postomats found"),
            @ApiResponse(code = 204, message = "Postomats not present")}
    )
    ResponseEntity<List<PostomatDto>> getAll();

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Postomat by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Postomat found"),
            @ApiResponse(code = 404, message = "Postomat not found")}
    )
    ResponseEntity<PostomatDto> get(@ApiParam(name = "id", value = "Postomat.id")
                                    @PathVariable Long id);

    @PostMapping
    @ApiOperation(value = "Create Postomat")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Postomat created"),
            @ApiResponse(code = 400, message = "Postomat not created")}
    )
    ResponseEntity<PostomatDto> create(@ApiParam(name = "postomat", value = "PostomatDto")
                                       @Valid @RequestBody PostomatDto postomatDto);

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update Postomat")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Postomat updated"),
            @ApiResponse(code = 400, message = "Postomat not updated")}
    )
    ResponseEntity<PostomatDto> update(@ApiParam(name = "id", value = "Postomat.id")
                                       @PathVariable Long id,
                                       @ApiParam(name = "postomat", value = "PostomatDto")
                                       @Valid @RequestBody PostomatDto postomatDto);

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Postomat by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Postomat deleted"),
            @ApiResponse(code = 404, message = "Postomat not found")}
    )
    ResponseEntity<Void> delete(@ApiParam(name = "id", value = "Postomat.id")
                                @PathVariable Long id);
}
