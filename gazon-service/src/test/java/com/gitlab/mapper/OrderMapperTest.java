package com.gitlab.mapper;

import com.gitlab.controller.AbstractIntegrationTest;
import com.gitlab.dto.OrderDto;
import com.gitlab.dto.SelectedProductDto;
import com.gitlab.enums.OrderStatus;
import com.gitlab.model.Order;
import com.gitlab.model.ShippingAddress;
import com.gitlab.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class OrderMapperTest extends AbstractIntegrationTest {


    @Autowired
    private OrderMapper mapper;

    @Autowired
    private  ShippingAddressMapper shippingAddressMapper;


    @Test
    void should_map_order_to_Dto(){
        Order order = new Order();
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setId(1L);
        shippingAddress.setAddress("address");
        shippingAddress.setDirections("direction");
        order.setShippingAddress(shippingAddress);
        order.setShippingDate(LocalDate.parse("2027-05-01"));
        order.setOrderCode("code");
        order.setCreateDateTime(LocalDateTime.now());
        order.setSum(new BigDecimal(5));
        order.setDiscount(new BigDecimal(6));
        order.setBagCounter((byte)5);
        order.setUser(new User());
        order.setSelectedProducts(Set.of());
        order.setOrderStatus(OrderStatus.IN_PROGRESS);

        OrderDto actualResult = mapper.toDto(order);

        assertNotNull(actualResult);
        assertEquals(order.getOrderCode(), actualResult.getOrderCode());
        assertEquals(order.getShippingAddress(), shippingAddressMapper.toEntity(actualResult.getShippingAddressDto()));
        assertEquals(order.getShippingDate(), actualResult.getShippingDate());
        assertEquals(order.getCreateDateTime(), actualResult.getCreateDateTime());
        assertEquals(order.getSum(), actualResult.getSum());
        assertEquals(order.getDiscount(), actualResult.getDiscount());
        assertEquals(order.getBagCounter(), actualResult.getBagCounter());
        assertEquals(order.getUser().getId(), actualResult.getUserId());
        assertEquals(order.getOrderStatus(), actualResult.getOrderStatus());

    }

    @Test
    void should_map_dto_to_order(){
        OrderDto orderDto = new OrderDto();

        SelectedProductDto selectedProductDto = new SelectedProductDto();
        selectedProductDto.setProductId(1L);
        selectedProductDto.setId(1L);
        orderDto.setOrderCode("123");
        orderDto.setUserId(1L);
        orderDto.setShippingDate(LocalDate.parse("2027-05-01"));
        orderDto.setCreateDateTime(LocalDateTime.now());
        orderDto.setSum(new BigDecimal(5));
        orderDto.setDiscount(new BigDecimal(6));
        orderDto.setBagCounter((byte) 5);
        orderDto.setOrderStatus(OrderStatus.ARRIVED);

        Order actualResult = mapper.toEntity(orderDto);

        assertNotNull(actualResult);
        assertEquals(orderDto.getOrderCode(), actualResult.getOrderCode());
        assertEquals(orderDto.getShippingDate(), actualResult.getShippingDate());
        assertEquals(orderDto.getCreateDateTime(), actualResult.getCreateDateTime());
        assertEquals(orderDto.getSum(), actualResult.getSum());
        assertEquals(orderDto.getDiscount(), actualResult.getDiscount());
        assertEquals(orderDto.getBagCounter(), actualResult.getBagCounter());
        assertEquals(orderDto.getUserId(), 1L);
        assertEquals(orderDto.getOrderStatus(), actualResult.getOrderStatus());

    }

}
