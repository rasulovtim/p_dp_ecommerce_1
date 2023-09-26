package com.gitlab.mapper;
import org.junit.jupiter.api.Test;
import com.gitlab.dto.OrderDto;
import com.gitlab.dto.SelectedProductDto;
import com.gitlab.model.Order;
import com.gitlab.model.ShippingAddress;
import com.gitlab.model.User;
import com.gitlab.service.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class OrderMapperTest {

    @InjectMocks
    private OrderMapper mapper = Mappers.getMapper(OrderMapper.class);

    @Mock
    private UserService userService;

    @Test
    void should_map_order_to_Dto(){
        Order order = new Order();
        order.setShippingAddress(new ShippingAddress());
        order.setShippingDate(LocalDate.parse("2027-05-01"));
        order.setOrderCode("code");
        order.setCreateDateTime(LocalDateTime.now());
        order.setSum(new BigDecimal(5));
        order.setDiscount(new BigDecimal(6));
        order.setBagCounter((byte)5);
        order.setUser(new User());
        order.setSelectedProducts(Set.of());
        order.setOrderStatus(Order.OrderStatus.IN_PROGRESS);

        OrderDto actualResult = mapper.toDto(order);

        assertNotNull(actualResult);
        assertEquals(order.getOrderCode(), actualResult.getOrderCode());
        assertEquals(order.getShippingAddress(), actualResult.getShippingAddress());
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
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setId(1L);
        orderDto.setSelectedProducts(Set.of(new SelectedProductDto()));
        orderDto.setShippingAddress(shippingAddress);
        orderDto.setUserId(2L);
        orderDto.setOrderCode("123");
        orderDto.setShippingDate(LocalDate.parse("2027-05-01"));
        orderDto.setCreateDateTime(LocalDateTime.now());
        orderDto.setSum(new BigDecimal(5));
        orderDto.setDiscount(new BigDecimal(6));
        orderDto.setBagCounter((byte) 5);
        orderDto.setOrderStatus(Order.OrderStatus.ARRIVED);

        Order actualResult = mapper.toEntity(orderDto);

        assertNotNull(actualResult);
        assertEquals(orderDto.getOrderCode(), actualResult.getOrderCode());
        assertEquals(orderDto.getShippingAddress(), actualResult.getShippingAddress());
        assertEquals(orderDto.getShippingDate(), actualResult.getShippingDate());
        assertEquals(orderDto.getCreateDateTime(), actualResult.getCreateDateTime());
        assertEquals(orderDto.getSum(), actualResult.getSum());
        assertEquals(orderDto.getDiscount(), actualResult.getDiscount());
        assertEquals(orderDto.getBagCounter(), actualResult.getBagCounter());
        assertEquals(orderDto.getUserId(), 2L);
        assertEquals(orderDto.getOrderStatus(), actualResult.getOrderStatus());

    }

}
