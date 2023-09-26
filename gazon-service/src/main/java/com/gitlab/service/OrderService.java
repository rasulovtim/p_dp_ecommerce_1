package com.gitlab.service;

import com.gitlab.dto.OrderDto;
import com.gitlab.mapper.OrderMapper;
import com.gitlab.mapper.SelectedProductMapper;
import com.gitlab.model.Order;
import com.gitlab.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    private final OrderMapper orderMapper;

    private final SelectedProductMapper selectedProductMapper;


    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<OrderDto> findAllDto() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(orderMapper::toDto).collect(Collectors.toList());
    }

    public Page<Order> getOrder(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return orderRepository.findAll(pageRequest);
    }

    public Page<OrderDto> getPageDto(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Order> examplePage = orderRepository.findAll(pageRequest);
        return examplePage.map(orderMapper::toDto);
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public Optional<OrderDto> findByIdDto(Long id) {
        return orderRepository.findById(id).map(orderMapper::toDto);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public OrderDto saveDto(OrderDto orderDto) {
        Order order = orderMapper.toEntity(orderDto);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    public Optional<Order> update(Long id, Order order) {
        Optional<Order> optionalSavedOrder = findById(id);
        Order savedOrder;
        if (optionalSavedOrder.isEmpty()) {
            return optionalSavedOrder;
        } else {
            savedOrder = optionalSavedOrder.get();
        }
        if (order.getShippingAddress() != null) {
            savedOrder.setShippingAddress(order.getShippingAddress());
        }
        if (order.getShippingDate() != null) {
            savedOrder.setShippingDate(order.getShippingDate());
        }
        if (order.getOrderCode() != null) {
            savedOrder.setOrderCode(order.getOrderCode());
        }
        if (order.getCreateDateTime() != null) {
            savedOrder.setCreateDateTime(order.getCreateDateTime());
        }
        if (order.getSum() != null) {
            savedOrder.setSum(order.getSum());
        }
        if (order.getDiscount() != null) {
            savedOrder.setDiscount(order.getDiscount());
        }
        if (order.getBagCounter() != null) {
            savedOrder.setBagCounter(order.getBagCounter());
        }
        if (order.getUser() != null) {
            savedOrder.setUser(order.getUser());
        }
        if (order.getSelectedProducts() != null) {
            savedOrder.setSelectedProducts(order.getSelectedProducts());
        }
        if (order.getOrderStatus() != null) {
            savedOrder.setOrderStatus(order.getOrderStatus());
        }

        return Optional.of(orderRepository.save(savedOrder));
    }

    public Optional<OrderDto> updateDto(Long id, OrderDto orderDto) {
        Optional<Order> optionalSavedOrder = findById(id);
        if (optionalSavedOrder.isEmpty()) {
            return Optional.empty();
        }
        Order savedOrder = optionalSavedOrder.get();
        if (orderDto.getShippingAddress() != null) {
            savedOrder.setShippingAddress(orderDto.getShippingAddress());
        }
        if (orderDto.getShippingDate() != null) {
            savedOrder.setShippingDate(orderDto.getShippingDate());
        }
        if (orderDto.getOrderCode() != null) {
            savedOrder.setOrderCode(orderDto.getOrderCode());
        }
        if (orderDto.getCreateDateTime() != null) {
            savedOrder.setCreateDateTime(orderDto.getCreateDateTime());
        }
        if (orderDto.getSum() != null) {
            savedOrder.setSum(orderDto.getSum());
        }
        if (orderDto.getDiscount() != null) {
            savedOrder.setDiscount(orderDto.getDiscount());
        }
        if (orderDto.getBagCounter() != null) {
            savedOrder.setBagCounter(orderDto.getBagCounter());
        }
        if (orderDto.getUserId() != null) {
            savedOrder.setUser(userService.findById(orderDto.getUserId()).get());
        }
        if (orderDto.getSelectedProducts() != null) {
            savedOrder.setSelectedProducts(orderDto.getSelectedProducts().stream().map(selectedProductMapper::toEntity).collect(Collectors.toSet()));
        }
        if (orderDto.getOrderStatus() != null) {
            savedOrder.setOrderStatus(orderDto.getOrderStatus());
        }

        savedOrder = orderRepository.save(savedOrder);
        return Optional.of(orderMapper.toDto(savedOrder));
    }

    public Optional<Order> delete(Long id) {
        Optional<Order> optionalSavedOrder = findById(id);
        if (optionalSavedOrder.isEmpty()) {
            return optionalSavedOrder;
        } else {
            orderRepository.deleteById(id);
            return optionalSavedOrder;
        }
    }

}
