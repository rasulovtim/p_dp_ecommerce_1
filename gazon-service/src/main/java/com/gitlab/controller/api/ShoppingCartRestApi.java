package com.gitlab.controller.api;


import com.gitlab.dto.ShoppingCartDto;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Shopping Cart REST")
@Tag(name = "Shopping Cart REST", description = "API for managing shopping carts")
@RequestMapping("/api/shopping-carts")
public interface ShoppingCartRestApi {

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Shopping Cart by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Shopping Cart found"),
            @ApiResponse(code = 404, message = "Shopping Cart not found")}
    )
    ResponseEntity<ShoppingCartDto> get(@ApiParam(name = "id", value = "ShoppingCart.id") @PathVariable Long id);

    @PostMapping
    @ApiOperation(value = "Create Shopping Cart")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Shopping Cart created"),
            @ApiResponse(code = 400, message = "Shopping Cart not created")}
    )
    ResponseEntity<ShoppingCartDto> create(@ApiParam(name = "shoppingCart", value = "ShoppingCartDto") @Valid @RequestBody ShoppingCartDto shoppingCartDto);

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update Shopping Cart")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Shopping Cart updated"),
            @ApiResponse(code = 400, message = "Shopping Cart not updated")}
    )
    ResponseEntity<ShoppingCartDto> update(@ApiParam(name = "id", value = "ShoppingCart.id") @PathVariable Long id,
                                           @ApiParam(name = "shoppingCart", value = "ShoppingCartDto") @Valid @RequestBody ShoppingCartDto shoppingCartDto);

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Shopping Cart by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Shopping Cart deleted"),
            @ApiResponse(code = 404, message = "Shopping Cart not found")}
    )
    ResponseEntity<Void> delete(@ApiParam(name = "id", value = "ShoppingCart.id") @PathVariable Long id);

    @GetMapping
    @ApiOperation(value = "Get all Shopping Carts")
    @ApiResponse(code = 200, message = "List of Shopping Carts")
    List<ShoppingCartDto> getAll();


}