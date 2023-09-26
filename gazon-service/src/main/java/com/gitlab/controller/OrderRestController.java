package com.gitlab.controller;

import com.gitlab.controller.api.OrderRestApi;
import com.gitlab.dto.OrderDto;
import com.gitlab.mapper.OrderMapper;
import com.gitlab.model.Order;
import com.gitlab.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class OrderRestController implements OrderRestApi {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Override
    public ResponseEntity<List<OrderDto>> getAll() {
        List<OrderDto> orders = orderService.findAllDto();
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(orders);
        }
    }

    @Override
    public ResponseEntity<OrderDto> get(Long id) {
        return orderService.findByIdDto(id)
                .map(value -> ResponseEntity.ok(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<OrderDto> create(OrderDto orderDto) {
        OrderDto savedOrderDto = orderService.saveDto(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedOrderDto);
    }

    @Override
    public ResponseEntity<OrderDto> update(Long id, OrderDto orderDto) {
        Optional<OrderDto> updateOrderDto = orderService.updateDto(id, orderDto);
        return updateOrderDto
                .map(dto -> ResponseEntity.ok(dto))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<OrderDto> delete(Long id) {
        Optional<Order> order = orderService.delete(id);
        if (order.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }
}
