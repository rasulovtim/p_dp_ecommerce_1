package com.gitlab.controllers.api.rest;

import com.gitlab.dto.StoreDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Store REST")
@Tag(name = "Store REST", description = "API store description")
public interface StoreRestApi {

    @GetMapping("/api/store")
    @ApiOperation(value = "Get all Store")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Store Page found"),
            @ApiResponse(code = 204, message = "Store Page not present")}
    )
    ResponseEntity<List<StoreDto>> getPage(@ApiParam(name = "page") @RequestParam(required = false, value = "page") Integer page,
                                           @ApiParam(name = "size") @RequestParam(required = false, value = "size") Integer size);

    @GetMapping("/api/store/{id}")
    @ApiOperation(value = "Get Store by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Store found"),
            @ApiResponse(code = 404, message = "Store not found")}
    )
    ResponseEntity<StoreDto> get(@ApiParam(name = "id", value = "Store.id") @PathVariable(value = "id") Long id);

    @PostMapping("/api/store")
    @ApiOperation(value = "Create Store")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Store created"),
            @ApiResponse(code = 400, message = "Store not created")}
    )
    ResponseEntity<StoreDto> create(@ApiParam(name = "store", value = "StoreDto") @Valid @RequestBody StoreDto storeDto);

    @PatchMapping("/api/store/{id}")
    @ApiOperation(value = "Update Store")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Store updated"),
            @ApiResponse(code = 400, message = "Store not updated")}
    )
    ResponseEntity<StoreDto> update(@ApiParam(name = "id", value = "Store.id") @PathVariable(value = "id") Long id,
                                    @ApiParam(name = "store", value = "StoreDto") @Valid @RequestBody StoreDto storeDto);



    @DeleteMapping("/api/store/{id}")
    @ApiOperation(value = "Delete Store by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Store deleted"),
            @ApiResponse(code = 404, message = "Store not found")}
    )
    ResponseEntity<Void> delete(@ApiParam(name = "id", value = "Store.id") @PathVariable(value = "id") Long id);
}









