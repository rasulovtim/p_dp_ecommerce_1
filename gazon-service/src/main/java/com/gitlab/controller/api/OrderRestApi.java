package com.gitlab.controller.api;

import com.gitlab.dto.OrderDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Order REST")
@Tag(name = "Order REST", description = "Order API description")
@RequestMapping("/api/order")
public interface OrderRestApi {

    @ApiOperation(value = "Get all orders")
    @GetMapping
    ResponseEntity<List<OrderDto>> getAll();

    @ApiOperation(value = "Get order by ID")
    @GetMapping("/{id}")
    ResponseEntity<OrderDto> get(@ApiParam(name = "id", value = "Order ID") @PathVariable Long id);

    @ApiOperation(value = "Create a new order")
    @PostMapping()
    ResponseEntity<OrderDto> create(@ApiParam(name = "orderDto", value = "Order details") @RequestBody OrderDto orderDto);

    @ApiOperation(value = "Update order by ID")
    @PutMapping("/{id}")
    ResponseEntity<OrderDto> update(@ApiParam(name = "id", value = "Order ID") @PathVariable Long id, @ApiParam(name = "OrderDto", value = "Update Order details") @RequestBody OrderDto orderDto);

    @ApiOperation(value = "Delete order by ID")
    @DeleteMapping("/{id}")
    ResponseEntity<OrderDto> delete(@ApiParam(name = "Order Dto", value = "Order Id") @PathVariable Long id);
}
