package com.gitlab.controllers.api.rest;


import com.gitlab.dto.ShoppingCartDto;
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

@Api(tags = "ShoppingCart REST")
@Tag(name = "ShoppingCart REST", description = "ShoppingCart API description")
public interface ShoppingCartRestApi {

    @GetMapping("/api/shopping-cart")
    @ApiOperation(value = "Get all ShoppingCarts")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ShoppingCarts found"),
            @ApiResponse(code = 204, message = "ShoppingCarts not present")}
    )
    ResponseEntity<List<ShoppingCartDto>> getPage(@ApiParam(name = "page") @RequestParam(required = false, value = "page") Integer page,
                                                  @ApiParam(name = "size") @RequestParam(required = false, value = "size") Integer size);

    @GetMapping("/api/shopping-cart/{id}")
    @ApiOperation(value = "Get ShoppingCart by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ShoppingCart found"),
            @ApiResponse(code = 404, message = "ShoppingCart not found")}
    )
    ResponseEntity<ShoppingCartDto> get(@ApiParam(name = "id", value = "ShoppingCart.id") @PathVariable(value = "id") Long id);

    @PostMapping("/api/shopping-cart")
    @ApiOperation(value = "Create ShoppingCart")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "ShoppingCart created"),
            @ApiResponse(code = 400, message = "ShoppingCart not created")}
    )
    ResponseEntity<ShoppingCartDto> create(@ApiParam(name = "ShoppingCart", value = "ShoppingCartDto")
                                           @Valid @RequestBody ShoppingCartDto shoppingCartDto);

    @PatchMapping("/api/shopping-cart/{id}")
    @ApiOperation(value = "Update ShoppingCart")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ShoppingCart updated"),
            @ApiResponse(code = 404, message = "Previous ShoppingCart not found"),
            @ApiResponse(code = 400, message = "ShoppingCart not updated")}
    )
    ResponseEntity<ShoppingCartDto> update(@ApiParam(name = "id", value = "ShoppingCart.id") @PathVariable(value = "id") Long id,
                                           @ApiParam(name = "ShoppingCart", value = "ShoppingCartDto")
                                           @Valid @RequestBody ShoppingCartDto shoppingCartDto);

    @DeleteMapping("/api/shopping-cart/{id}")
    @ApiOperation(value = "Delete ShoppingCart by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ShoppingCart deleted"),
            @ApiResponse(code = 404, message = "ShoppingCart not found")}
    )
    ResponseEntity<Void> delete(@ApiParam(name = "id", value = "ShoppingCart.id") @PathVariable(value = "id") Long id);

}