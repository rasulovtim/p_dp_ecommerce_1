package com.gitlab.controller.api;

import com.gitlab.dto.PaymentDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    ResponseEntity<List<PaymentDto>> getAll();

    @ApiOperation(value = "Get payment by Id")
    @GetMapping("/{id}")
    ResponseEntity<PaymentDto> get(@ApiParam(name = "id", value = "Payment Id") @PathVariable Long id);

    @ApiOperation(value = "Create a new payment")
    @PostMapping()
    ResponseEntity<PaymentDto> create(@ApiParam(name = "paymentDto", value = "Payment details") @RequestBody PaymentDto paymentDto);

    @ApiOperation(value = "Update payment by Id")
    @PatchMapping("/{id}")
    ResponseEntity<PaymentDto> update(@ApiParam(name = "id", value = "Payment Id") @PathVariable Long id, @ApiParam(name = "PaymentDto", value = "Update Payment details") @RequestBody PaymentDto paymentDto);

    @ApiOperation(value = "Delete payment by ID")
    @DeleteMapping("/{id}")
    ResponseEntity<PaymentDto> delete(@ApiParam(name = "id", value = "Payment Id") @PathVariable Long id);
}
