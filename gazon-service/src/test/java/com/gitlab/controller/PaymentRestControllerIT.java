package com.gitlab.controller;

import com.gitlab.dto.*;
import com.gitlab.mapper.PaymentMapper;
import com.gitlab.model.BankCard;
import com.gitlab.model.Order;
import com.gitlab.model.Payment;
import com.gitlab.model.User;
import com.gitlab.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

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

    @Test
    void should_get_payment_by_id() throws Exception {


        long id = 1L;

//        Payment payment = new Payment();
//        BankCard bankCard = new BankCard();
//        bankCard.setId(1L);
//        User user = new User();
//        user.setId(1L);
//        Order order = new Order();
//        order.setId(1L);
//        payment.setId(1L);
//        payment.setOrder(new Order(1L));
//        payment.setBankCard(bankCard);
//        payment.setPaymentStatus(Payment.PaymentStatus.PAID);
//        payment.setCreateDateTime(LocalDateTime.now());
//        payment.setOrder(order);
//        payment.setSum(new BigDecimal(200));
//        payment.setUser(user);
//        paymentService.save(payment);


        String expected = objectMapper.writeValueAsString(
                paymentMapper.toDto(
                        paymentService
                                .findById(id)
                                .orElse(null))
        );

        mockMvc.perform(get(PAYMENT_URI + "/{id}", id))
                .andDo(print())
//                .andExpect(status().isPartialContent())
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
        PaymentDto paymentDto = generatePaymentDto();

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
        paymentDto.setId(1L);
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
        long id = 9000L;
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
    void should_remove_payment_by_id() throws Exception {
        long id = 1L;
        PaymentDto paymentDto = generatePaymentDto();
        mockMvc.perform(delete(PAYMENT_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get(PAYMENT_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private PaymentDto generatePaymentDto() {

        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setId(1L);
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setId(1L);
        bankCardDto.setCardNumber("4828078439696627");
        bankCardDto.setDueDate(LocalDate.parse("2029-09-22"));
        bankCardDto.setSecurityCode(354);
        paymentDto.setBankCardDto(bankCardDto);
        paymentDto.setPaymentStatus(Payment.PaymentStatus.PAID);
        paymentDto.setCreateDateTime(LocalDateTime.now());
        paymentDto.setOrderId(1L);
        paymentDto.setSum(new BigDecimal(500));
        paymentDto.setUserId(1L);

        return paymentDto;
    }
}