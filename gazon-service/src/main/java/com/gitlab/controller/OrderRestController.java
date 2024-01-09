package com.gitlab.controller;

import com.gitlab.controllers.api.rest.OrderRestApi;
import com.gitlab.dto.OrderDto;
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

    public ResponseEntity<List<OrderDto>> getPage(Integer page, Integer size) {
        var orderPage = orderService.getPageDto(page, size);
        if (orderPage == null || orderPage.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orderPage.getContent());
    }

    @Override
    public ResponseEntity<OrderDto> get(Long id) {
        return orderService.findByIdDto(id)
                .map(ResponseEntity::ok)
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
                .map(ResponseEntity::ok)
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
