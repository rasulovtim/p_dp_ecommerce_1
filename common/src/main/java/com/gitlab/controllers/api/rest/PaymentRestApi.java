package com.gitlab.controllers.api.rest;

import com.gitlab.dto.PaymentDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Payment REST")
@Tag(name = "Payment REST", description = "Payment API description")
public interface PaymentRestApi {

    @GetMapping("/api/payment")
    @ApiOperation(value = "Get payment page")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "payment page found"),
            @ApiResponse(code = 204, message = "payment page not present")}
    )
    ResponseEntity<List<PaymentDto>> getPage(@ApiParam(name = "page") @RequestParam(required = false, value = "page") Integer page,
                                             @ApiParam(name = "size") @RequestParam(required = false, value = "size") Integer size);

    @ApiOperation(value = "Get payment by Id")
    @GetMapping("/api/payment/{id}")
    ResponseEntity<PaymentDto> get(@ApiParam(name = "id", value = "Payment Id") @PathVariable Long id);

    @ApiOperation(value = "Create a new payment")
    @PostMapping("/api/payment")
    ResponseEntity<PaymentDto> create(@ApiParam(name = "paymentDto", value = "Payment details") @RequestBody PaymentDto paymentDto);

    @ApiOperation(value = "Update payment by Id")
    @PatchMapping("/api/payment/{id}")
    ResponseEntity<PaymentDto> update(@ApiParam(name = "id", value = "Payment Id") @PathVariable Long id, @ApiParam(name = "PaymentDto", value = "Update Payment details") @RequestBody PaymentDto paymentDto);

    @ApiOperation(value = "Delete payment by ID")
    @DeleteMapping("/api/payment/{id}")
    ResponseEntity<PaymentDto> delete(@ApiParam(name = "Payment Dto", value = "Payment Id") @PathVariable Long id);
}
