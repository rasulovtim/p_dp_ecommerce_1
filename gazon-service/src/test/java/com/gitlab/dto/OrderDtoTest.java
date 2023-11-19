package com.gitlab.dto;

import com.gitlab.enums.OrderStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderDtoTest extends AbstractDtoTest {

    @Test
    void test_valid_order() {
        var orderDto = generateOrderDto();
        assertTrue(validator.validate(orderDto).isEmpty());
    }

    @Test
    void test_invalid_shipping_address(){
        var orderDto = generateOrderDto();
        orderDto.setShippingAddressDto(null);

        assertFalse(validator.validate(orderDto).isEmpty());
    }

    @Test
    void test_invalid_shipping_date(){
        var orderDto = generateOrderDto();
        orderDto.setShippingDate(null);

        assertFalse(validator.validate(orderDto).isEmpty());
    }

    @Test
    void test_invalid_order_code(){
        var orderDto = generateOrderDto();
        orderDto.setOrderCode("");

        assertFalse(validator.validate(orderDto).isEmpty());
    }

    @Test
    void test_invalid_create_date_time(){
        var orderDto = generateOrderDto();
        orderDto.setCreateDateTime(null);

        assertFalse(validator.validate(orderDto).isEmpty());
    }

    @Test
    void test_invalid_sum(){
        var orderDto = generateOrderDto();
        orderDto.setSum(null);

        assertFalse(validator.validate(orderDto).isEmpty());
    }

    @Test
    void test_invalid_discount(){
        var orderDto = generateOrderDto();
        orderDto.setDiscount(null);

        assertFalse(validator.validate(orderDto).isEmpty());
    }

    @Test
    void test_invalid_bagCounter(){
        var orderDto = generateOrderDto();
        orderDto.setBagCounter(null);

        assertFalse(validator.validate(orderDto).isEmpty());
    }

    @Test
    void test_invalid_userId(){
        var orderDto = generateOrderDto();
        orderDto.setUserId(null);

        assertFalse(validator.validate(orderDto).isEmpty());
    }

    OrderDto generateOrderDto() {
        OrderDto orderDto = new OrderDto();
        ShippingAddressDto shippingAddressDto = new ShippingAddressDto();
        shippingAddressDto.setId(1L);
        orderDto.setSelectedProducts(Set.of(new SelectedProductDto()));
        orderDto.setShippingAddressDto(shippingAddressDto);
        orderDto.setUserId(1L);
        orderDto.setOrderCode("123");
        orderDto.setShippingDate(LocalDate.parse("2027-05-01"));
        orderDto.setCreateDateTime(LocalDateTime.now());
        orderDto.setSum(new BigDecimal(5));
        orderDto.setDiscount(new BigDecimal(6));
        orderDto.setBagCounter((byte) 5);
        orderDto.setOrderStatus(OrderStatus.ARRIVED);
        return orderDto;
    }

}
