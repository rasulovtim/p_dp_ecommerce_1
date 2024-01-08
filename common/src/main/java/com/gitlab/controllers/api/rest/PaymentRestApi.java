package com.gitlab.controller.api;

import com.gitlab.dto.PaymentDto;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Payment REST")
@Tag(name = "Payment REST", description = "Payment API description")
@RequestMapping("/api/payment")
public interface PaymentRestApi {

    @ApiOperation(value = "Get all payments")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Payments found"),
            @ApiResponse(code = 404, message = "Payments not present")}
    )
    ResponseEntity<List<PaymentDto>> getAll();

    @ApiOperation(value = "Get payment by Id")
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Payment found"),
            @ApiResponse(code = 404, message = "Payment not present")}
    )
    ResponseEntity<PaymentDto> get(@ApiParam(name = "id", value = "Payment Id") @PathVariable Long id);

    @ApiOperation(value = "Create a new payment")
    @PostMapping()
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Payment created"),
            @ApiResponse(code = 400, message = "Payment not created")}
    )
    ResponseEntity<PaymentDto> create(@ApiParam(name = "paymentDto", value = "Payment details") @RequestBody PaymentDto paymentDto);

    @ApiOperation(value = "Update payment by Id")
    @PatchMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Payment updated"),
            @ApiResponse(code = 404, message = "Payment not found")}
    )
    ResponseEntity<PaymentDto> update(@ApiParam(name = "id", value = "Payment Id") @PathVariable Long id, @ApiParam(name = "PaymentDto", value = "Update Payment details") @RequestBody PaymentDto paymentDto);

    @ApiOperation(value = "Delete payment by ID")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Payment deleted"),
            @ApiResponse(code = 404, message = "Payment not found")}
    )
    ResponseEntity<PaymentDto> delete(@ApiParam(name = "id", value = "Payment Id") @PathVariable Long id);
}
