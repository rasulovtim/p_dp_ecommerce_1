package com.gitlab.controller;

import com.gitlab.dto.*;
import com.gitlab.mapper.OrderMapper;
import com.gitlab.model.Order;
import com.gitlab.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.org.hamcrest.CoreMatchers.equalTo;
import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;

public class OrderRestControllerIT extends AbstractIntegrationTest {

    private static final String ORDER_URN = "/api/order";
    private static final String ORDER_URI = URL + ORDER_URN;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @Test
    void should_get_all_orders() throws Exception {
        List<OrderDto> orders = orderService.findAllDto();
        String expected = objectMapper.writeValueAsString(orders);

        mockMvc.perform(get(ORDER_URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));

    }

    @Test
    void should_get_order_by_id() throws Exception{
        long id = 1;
        var orderDto = orderService.findByIdDto(id).orElse(null);
        String expected = objectMapper.writeValueAsString(orderDto);

        mockMvc.perform(get(ORDER_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_create_order() throws Exception{
        OrderDto orderDto = generateOrderDto();

        String jsonOrderDto = objectMapper.writeValueAsString(orderDto);

        mockMvc.perform(post(ORDER_URI)
                        .content(jsonOrderDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Transactional
    @Test
    void should_update_order_by_id() throws Exception {
        long id = 1L;
        int numberOfEntitiesExpected = orderService.findAll().size();
        OrderDto orderDto = generateOrderDto();
        String jsonOrderDto = objectMapper.writeValueAsString(orderDto);

        orderDto.setId(id);
        String expected = objectMapper.writeValueAsString(orderDto);

        mockMvc.perform(put(ORDER_URI + "/{id}", id)
                        .content(jsonOrderDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected))
                .andExpect(result -> assertThat(orderService.findAll().size(),
                        equalTo(numberOfEntitiesExpected)));
    }

    @Test
    void should_delete_order_by_id() throws Exception {
        OrderDto orderDto = orderService.saveDto(generateOrderDto());
        long id = orderDto.getId();
        mockMvc.perform(delete(ORDER_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get(ORDER_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_not_found_when_get_order_by_non_existent_id() throws Exception {
        long id = 10L;
        mockMvc.perform(get(ORDER_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_not_found_when_update_order_by_non_existent_id() throws Exception {
        long id = 10L;
        OrderDto orderDto = generateOrderDto();
        String jsonOrderDto = objectMapper.writeValueAsString(orderDto);

        mockMvc.perform(put(ORDER_URI + "/{id}", id)  // Заменяем patch на put
                        .content(jsonOrderDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());  // Ожидаем статус 404 Not Found
    }



    private OrderDto generateOrderDto() {
        OrderDto orderDto = new OrderDto();
        ShippingAddressDto shippingAddressDto = new ShippingAddressDto();
        shippingAddressDto.setId(1L);
        shippingAddressDto.setAddress("dfdefdf");
        shippingAddressDto.setDirections("dgfgg");
        orderDto.setSelectedProducts(Set.of(new SelectedProductDto()));
        orderDto.setShippingAddressDto(shippingAddressDto);
        orderDto.setUserId(1L);
        orderDto.setOrderCode("123");
        orderDto.setShippingDate(LocalDate.parse("2027-05-01"));
        orderDto.setCreateDateTime(LocalDateTime.now());
        orderDto.setSum(new BigDecimal(5));
        orderDto.setDiscount(new BigDecimal(6));
        orderDto.setBagCounter((byte)5);
        orderDto.setOrderStatus(Order.OrderStatus.DONE);
        return orderDto;
    }

}
