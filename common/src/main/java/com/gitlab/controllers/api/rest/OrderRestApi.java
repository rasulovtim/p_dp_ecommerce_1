package com.gitlab.controllers.api.rest;

import com.gitlab.dto.OrderDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api(tags = "Order REST")
@Tag(name = "Order REST", description = "API Order description")
public interface OrderRestApi {

    @ApiOperation(value = "Get all orders")
    @GetMapping("/api/order")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Order found"),
            @ApiResponse(code = 204, message = "Order not present")}
    )
    ResponseEntity<List<OrderDto>> getPage(@ApiParam(name = "page") @RequestParam(required = false, value = "page") Integer page,
                                           @ApiParam(name = "size") @RequestParam(required = false, value = "size") Integer size);;

    @ApiOperation(value = "Get order by ID")
    @GetMapping("/api/order/{id}")
    ResponseEntity<OrderDto> get(@ApiParam(name = "id", value = "Order ID") @PathVariable (value = "id") Long id);

    @ApiOperation(value = "Create a new order")
    @PostMapping("/api/order")
    ResponseEntity<OrderDto> create(@ApiParam(name = "orderDto", value = "Order details") @RequestBody OrderDto orderDto);

    @ApiOperation(value = "Update order by ID")
    @PutMapping("/api/order/{id}")
    ResponseEntity<OrderDto> update(@ApiParam(name = "id", value = "Order ID") @PathVariable (value = "id") Long id, @ApiParam(name = "OrderDto", value = "Update Order details") @RequestBody OrderDto orderDto);

    @ApiOperation(value = "Delete order by ID")
    @DeleteMapping("/api/order/{id}")
    ResponseEntity<OrderDto> delete(@ApiParam(name = "Order Dto", value = "Order Id") @PathVariable (value ="id") Long id);
}
