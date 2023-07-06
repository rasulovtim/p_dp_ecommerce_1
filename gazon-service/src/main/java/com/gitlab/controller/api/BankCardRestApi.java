package com.gitlab.controller.api;

import com.gitlab.dto.BankCardDto;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "BankCard REST")
@Tag(name = "BankCard REST", description = "BankCard API description")
@RequestMapping("/api/bank-card")
public interface BankCardRestApi {


    @GetMapping
    @ApiOperation(value = "Get all BankCards")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "BankCards found"),
            @ApiResponse(code = 204, message = "BankCards not present")}
    )
    ResponseEntity<List<BankCardDto>> getAll();

    @GetMapping("/{id}")
    @ApiOperation(value = "Get BankCard by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "BankCard found"),
            @ApiResponse(code = 404, message = "BankCard not found")}
    )
    ResponseEntity<BankCardDto> get(@ApiParam(name = "id", value = "BankCard.id") @PathVariable Long id);



    @PostMapping
    @ApiOperation(value = "Create BankCard")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "BankCard created"),
            @ApiResponse(code = 400, message = "BankCard not created")}
    )
    ResponseEntity<BankCardDto> create(@ApiParam(name = "bankCard", value = "BankCardDto") @Valid @RequestBody BankCardDto bankCardDto);


    @PatchMapping("/{id}")
    @ApiOperation(value = "Update BankCard")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "BankCard updated"),
            @ApiResponse(code = 400, message = "BankCard not updated")}
    )
    ResponseEntity<BankCardDto> update(@ApiParam(name = "id", value = "BankCard.id") @PathVariable Long id,
                                      @ApiParam(name = "bankCard", value = "BankCardDto") @Valid @RequestBody BankCardDto bankCardDto);


    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete BankCard by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "BankCard deleted"),
            @ApiResponse(code = 404, message = "BankCard not found")}
    )
    ResponseEntity<Void> delete(@ApiParam(name = "id", value = "BankCard.id") @PathVariable Long id);
}
