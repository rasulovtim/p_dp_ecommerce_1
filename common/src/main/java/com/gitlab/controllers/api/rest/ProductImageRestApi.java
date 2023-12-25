package com.gitlab.controllers.api.rest;

import com.gitlab.dto.ProductImageDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
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

@Api(tags = "ProductImage REST")
@Tag(name = "ProductImage REST", description = "ProductImage API description")
public interface ProductImageRestApi {


    @GetMapping("/api/images")
    @ApiOperation(value = "Get Page of Product images")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product image Page found"),
            @ApiResponse(code = 204, message = "Product image Page not present")}
    )
    ResponseEntity<Page<ProductImageDto>> getPage(@ApiParam(name = "page") @RequestParam(required = false, value = "page") Integer page,
                                                 @ApiParam(name = "size") @RequestParam(required = false, value = "size") Integer size);

    @GetMapping("/api/images/{id}")
    @ApiOperation(value = "Get ProductImage by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ProductImage found"),
            @ApiResponse(code = 206, message = "ProductImage found, but as json not jpg"),
            @ApiResponse(code = 404, message = "ProductImage not found")}
    )
    ResponseEntity<?> get(@PathVariable(value = "id") Long id);


    @GetMapping("/api/images/product_id/{id}")
    @ApiOperation(value = "Get ProductImage by Product id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ProductImage by product id found"),
            @ApiResponse(code = 206, message = "ProductImage found, but as json not jpg"),
            @ApiResponse(code = 404, message = "ProductImage by product id not found")}
    )
    ResponseEntity<List<ProductImageDto>> getAllByProduct(@PathVariable(value = "id") Long id);


    @ApiOperation(value = "Create a new ProductImage")
    @PostMapping("/api/images")
    ResponseEntity<ProductImageDto> create(@ApiParam(name = "productImageDto", value = "Product images details") @RequestBody ProductImageDto productImageDto);


    @PatchMapping(value = "/api/images/{id}")
    @ApiOperation(value = "Update Product Image")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product Image updated"),
            @ApiResponse(code = 404, message = "Product Image not found")}
    )
    ResponseEntity<ProductImageDto> update(@ApiParam(name = "id", value = "ProductImage.id") @PathVariable(value = "id") Long id,
                                          @ApiParam(name = "product_image", value = "ProductImageDto") @Valid @RequestBody ProductImageDto productImageDto);

    @DeleteMapping("/api/images/{id}")
    @ApiOperation(value = "Delete ProductImage by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ProductImage deleted"),
            @ApiResponse(code = 404, message = "ProductImage not found")}
    )
    ResponseEntity<Void> delete(@PathVariable(value = "id") Long id);
}