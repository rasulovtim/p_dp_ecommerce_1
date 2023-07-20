package com.gitlab.controller.api;

import com.gitlab.dto.ProductImageDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "ProductImage REST")
@Tag(name = "ProductImage REST", description = "ProductImage API description")
@RequestMapping("/api/images")
public interface ProductImageRestApi {


    @GetMapping
    @ApiOperation(value = "Get all ProductImages IDs")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ProductImages found"),
            @ApiResponse(code = 204, message = "ProductImages not present")}
    )
    ResponseEntity<long[]> getAll();


    @GetMapping("/{id}")
    @ApiOperation(value = "Get ProductImage by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ProductImage found"),
            @ApiResponse(code = 206, message = "ProductImage found, but as json not jpg"),
            @ApiResponse(code = 404, message = "ProductImage not found")}
    )
    ResponseEntity<?> get(@PathVariable("id") Long id);


    @PatchMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "Update ProductImage")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ProductImage updated"),
            @ApiResponse(code = 404, message = "ProductImage not found")}
    )
    ResponseEntity<ProductImageDto> update(@RequestParam("file") MultipartFile files,
                                           @PathVariable Long id) throws IOException;


    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete ProductImage by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ProductImage deleted"),
            @ApiResponse(code = 404, message = "ProductImage not found")}
    )
    ResponseEntity<Void> delete(@PathVariable Long id);

}