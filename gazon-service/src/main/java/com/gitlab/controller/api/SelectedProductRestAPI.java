package com.gitlab.controller.api;

import com.gitlab.dto.SelectedProductDto;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "SelectedProduct REST")
@Tag(name = "SelectedProduct REST", description = "SelectedProduct API description")
@RequestMapping("/api/selected_product")
public interface SelectedProductRestAPI {


    @GetMapping
    @ApiOperation(value = "Get all SelectedProduct")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SelectedProduct found"),
            @ApiResponse(code = 204, message = "SelectedProduct not present")}
    )
    ResponseEntity<List<SelectedProductDto>> getAll();

    @GetMapping("/{id}")
    @ApiOperation(value = "Get SelectedProduct by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SelectedProduct found"),
            @ApiResponse(code = 404, message = "SelectedProduct not found")}
    )
    ResponseEntity<SelectedProductDto> get(@ApiParam(name = "id", value = "SelectedProduct.id") @PathVariable Long id,
                                           @ApiParam(name = "no_sum_no_weight")
                                           @RequestParam(required = false) boolean no_sum_no_weight);

    @PostMapping
    @ApiOperation(value = "Create SelectedProduct")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "SelectedProduct created"),
            @ApiResponse(code = 400, message = "SelectedProduct not created")}
    )
    ResponseEntity<SelectedProductDto> create(@ApiParam(name = "SelectedProduct", value = "SelectedProductDto")
                                      @Valid @RequestBody SelectedProductDto selectedProductDto);

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update SelectedProduct")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SelectedProduct updated"),
            @ApiResponse(code = 404, message = "Previous SelectedProduct not found"),
            @ApiResponse(code = 400, message = "SelectedProduct not updated")}
    )
    ResponseEntity<SelectedProductDto> update(@ApiParam(name = "id", value = "SelectedProduct.id") @PathVariable Long id,
                                      @ApiParam(name = "SelectedProduct", value = "SelectedProductDto")
                                      @Valid @RequestBody SelectedProductDto selectedProductDto);

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete SelectedProduct by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SelectedProduct deleted"),
            @ApiResponse(code = 404, message = "SelectedProduct not found")}
    )
    ResponseEntity<Void> delete(@ApiParam(name = "id", value = "SelectedProduct.id") @PathVariable Long id);

}
