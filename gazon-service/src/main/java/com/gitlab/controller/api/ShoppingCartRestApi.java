package com.gitlab.controller.api;


import com.gitlab.dto.ShoppingCartDto;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Shopping Cart REST")
@Tag(name = "Shopping Cart REST", description = "Shopping Cart API description")
@RequestMapping("/api/shopping-cart")
public interface ShoppingCartRestApi {

    @GetMapping
    @ApiOperation(value = "Get all Shopping Carts", notes = "Get a list of all Shopping Carts")
    ResponseEntity<List<ShoppingCartDto>> getAll();

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Shopping Cart by id", notes = "Get a specific Shopping Cart by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Shopping Cart found"),
            @ApiResponse(code = 404, message = "Shopping Cart not found")}
    )
    ResponseEntity<ShoppingCartDto> get(@ApiParam(name = "id", value = "Shopping Cart ID") @PathVariable Long id);

    @PostMapping
    @ApiOperation(value = "Create Shopping Cart", notes = "Create a new Shopping Cart")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Shopping Cart created"),
            @ApiResponse(code = 400, message = "Invalid request")}
    )
    ResponseEntity<ShoppingCartDto> create(@ApiParam(name = "shoppingCartDto", value = "Shopping Cart data") @Valid @RequestBody ShoppingCartDto shoppingCartDto);

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update Shopping Cart", notes = "Update an existing Shopping Cart by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Shopping Cart updated"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Shopping Cart not found")}
    )
    ResponseEntity<ShoppingCartDto> update(@ApiParam(name = "id", value = "Shopping Cart ID") @PathVariable Long id,
                                           @ApiParam(name = "shoppingCartDto", value = "Shopping Cart data") @Valid @RequestBody ShoppingCartDto shoppingCartDto);

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Shopping Cart by id", notes = "Delete a specific Shopping Cart by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Shopping Cart deleted"),
            @ApiResponse(code = 404, message = "Shopping Cart not found")}
    )
    ResponseEntity<Void> delete(@ApiParam(name = "id", value = "Shopping Cart ID") @PathVariable Long id);
}

