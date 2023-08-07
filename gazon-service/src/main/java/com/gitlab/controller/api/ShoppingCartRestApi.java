package com.gitlab.controller.api;


import com.gitlab.dto.ShoppingCartDto;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Shopping Cart REST")
@Tag(name = "Shopping Cart REST", description = "API for shopping carts")
@RequestMapping("/api/shopping_cart")
public interface ShoppingCartRestApi {



    @GetMapping("/{id}")
    @ApiOperation(value = "Get Shopping Cart by ID")
    ResponseEntity<ShoppingCartDto> getById(
            @ApiParam(name = "id", value = "Shopping Cart ID") @PathVariable Long id
    );

    @PostMapping
    @ApiOperation(value = "Create Shopping Cart")
    ResponseEntity<ShoppingCartDto> create(
            @ApiParam(name = "shoppingCart", value = "ShoppingCartDto") @Valid @RequestBody ShoppingCartDto shoppingCartDto
    );

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update Shopping Cart")
    ResponseEntity<ShoppingCartDto> update(
            @ApiParam(name = "id", value = "Shopping Cart ID") @PathVariable Long id,
            @ApiParam(name = "shoppingCart", value = "ShoppingCartDto") @Valid @RequestBody ShoppingCartDto shoppingCartDto
    );

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Shopping Cart by ID")
    ResponseEntity<Void> delete(
            @ApiParam(name = "id", value = "Shopping Cart ID") @PathVariable Long id
    );
}