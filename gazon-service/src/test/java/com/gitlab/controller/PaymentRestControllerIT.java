package com.gitlab.controller;

import com.gitlab.dto.*;
import com.gitlab.mapper.PaymentMapper;
import com.gitlab.model.Order;
import com.gitlab.model.Payment;
import com.gitlab.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.org.hamcrest.CoreMatchers.equalTo;
import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;

class PaymentRestControllerIT extends AbstractIntegrationTest {

    private static final String PAYMENT_URN = "/api/payment";
    private static final String PAYMENT_URI = URL + PAYMENT_URN;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentMapper paymentMapper;

//    @Test
//    void should_get_all_payments() throws Exception {
//        List<PaymentDto> payments = paymentService.findAllDto();
//
//        String expected = objectMapper.writeValueAsString(payments);
//
//        mockMvc.perform(get(PAYMENT_URI))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().json(expected));
//    }

    @Test
    void should_get_all_payments() throws Exception {

        String expected = objectMapper.writeValueAsString(
                paymentService
                .findAll()
                .stream()
                .map(paymentMapper::toDto)
                .collect(Collectors.toList())
        );

        mockMvc.perform(get(PAYMENT_URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

//    @Test
//    void should_get_page() throws Exception {
//        int page = 0;
//        int size = 2;
//        String parameters = "?page=" + page + "&size=" + size;
//
//        var response = paymentService.getPage(page, size);
//        assertFalse(response.getContent().isEmpty());
//
//        var expected = objectMapper.writeValueAsString(new PageImpl<>(
//                response.getContent().stream().map(paymentMapper::toDto).toList(),
//                response.getPageable(),
//                response.getTotalElements()
//        ));
//
//        mockMvc.perform(get(PAYMENT_URI + parameters))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().json(expected));
//    }

    @Test
    void should_get_payment_by_id() throws Exception {
        long id = 1L;

        String expected = objectMapper.writeValueAsString(
                paymentMapper.toDto(
                        paymentService
                                .findById(id)
                                .orElse(null))
        );

        mockMvc.perform(get(PAYMENT_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_return_not_found_when_get_payment_by_non_existent_id() throws Exception {
        long id = 10L;
        mockMvc.perform(get(PAYMENT_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_create_payment() throws Exception {
        PaymentDto paymentDto = new PaymentDto();

        String jsonPaymentDto = objectMapper.writeValueAsString(paymentDto);

        mockMvc.perform(post(PAYMENT_URI)
                        .content(jsonPaymentDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void should_update_payment_by_id() throws Exception {
        long id = 1L;
        int numberOfEntitiesExpected = paymentService.findAll().size();

        PaymentDto paymentDto = generatePaymentDto();

        String jsonPaymentDto = objectMapper.writeValueAsString(paymentDto);
        paymentDto.setId(id);
        String expected = objectMapper.writeValueAsString(paymentDto);

        mockMvc.perform(patch(PAYMENT_URI + "/{id}", id)
                        .content(jsonPaymentDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected))
                .andExpect(result -> assertThat(paymentService.findAll().size(),
                        equalTo(numberOfEntitiesExpected)));
    }

    @Test
    void should_return_not_found_when_update_payment_by_non_existent_id() throws Exception {
        long id = 1L;
        PaymentDto paymentDto = generatePaymentDto();

        String jsonPaymentDto = objectMapper.writeValueAsString(paymentDto);

        mockMvc.perform(patch(PAYMENT_URI + "/{id}", id)
                        .content(jsonPaymentDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_delete_payment_by_id() throws Exception {
        long id = 1L;
        mockMvc.perform(delete(PAYMENT_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get(PAYMENT_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
    private PaymentDto generatePaymentDto() {
        PaymentDto paymentDto = new PaymentDto();
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setId(1L);
        bankCardDto.setCardNumber("4828078439696627");
        bankCardDto.setDueDate(LocalDate.parse("2029-09-22"));
        bankCardDto.setSecurityCode(354);
        paymentDto.setBankCardDto(bankCardDto);
        paymentDto.setPaymentStatus(Payment.PaymentStatus.PAID);
        paymentDto.setCreateDateTime(LocalDateTime.now());

//        OrderDto orderDto = new OrderDto();
//        ShippingAddressDto shippingAddressDto = new ShippingAddressDto();
//        shippingAddressDto.setId(1L);
//        shippingAddressDto.setAddress("1dfdefdf1");
//        shippingAddressDto.setDirections("1dgfgg1");
//        orderDto.setSelectedProducts(Set.of(new SelectedProductDto()));
//        orderDto.setShippingAddressDto(shippingAddressDto);
//        orderDto.setUserId(1L);
//        orderDto.setOrderCode("1123");
//        orderDto.setShippingDate(LocalDate.parse("2027-05-11"));
//        orderDto.setCreateDateTime(LocalDateTime.now());
//        orderDto.setSum(new BigDecimal(115));
//        orderDto.setDiscount(new BigDecimal(16));
//        orderDto.setBagCounter((byte)15);
//        orderDto.setOrderStatus(Order.OrderStatus.DONE);
        paymentDto.setOrderId(1L);
        paymentDto.setSum(new BigDecimal(500));
        paymentDto.setUserId(1L);
        return paymentDto;
    }
}