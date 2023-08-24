package com.gitlab.controller.api;


import com.gitlab.dto.ShoppingCartDto;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "ShoppingCart REST")
@Tag(name = "ShoppingCart REST", description = "ShoppingCart API description")
@RequestMapping("/api/shopping_cart")
public interface ShoppingCartRestApi {

    @GetMapping
    @ApiOperation(value = "Get all ShoppingCarts")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ShoppingCarts found"),
            @ApiResponse(code = 204, message = "ShoppingCarts not present")}
    )
    ResponseEntity<List<ShoppingCartDto>> getAll();

    @GetMapping("/{id}")
    @ApiOperation(value = "Get ShoppingCart by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ShoppingCart found"),
            @ApiResponse(code = 404, message = "ShoppingCart not found")}
    )
    ResponseEntity<ShoppingCartDto> get(@ApiParam(name = "id", value = "ShoppingCart.id") @PathVariable Long id);

    @PostMapping
    @ApiOperation(value = "Create ShoppingCart")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "ShoppingCart created"),
            @ApiResponse(code = 400, message = "ShoppingCart not created")}
    )
    ResponseEntity<ShoppingCartDto> create(@ApiParam(name = "ShoppingCart", value = "ShoppingCartDto")
                                           @Valid @RequestBody ShoppingCartDto shoppingCartDto);

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update ShoppingCart")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ShoppingCart updated"),
            @ApiResponse(code = 404, message = "Previous ShoppingCart not found"),
            @ApiResponse(code = 400, message = "ShoppingCart not updated")}
    )
    ResponseEntity<ShoppingCartDto> update(@ApiParam(name = "id", value = "ShoppingCart.id") @PathVariable Long id,
                                           @ApiParam(name = "ShoppingCart", value = "ShoppingCartDto")
                                           @Valid @RequestBody ShoppingCartDto shoppingCartDto);

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete ShoppingCart by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ShoppingCart deleted"),
            @ApiResponse(code = 404, message = "ShoppingCart not found")}
    )
    ResponseEntity<Void> delete(@ApiParam(name = "id", value = "ShoppingCart.id") @PathVariable Long id);

}