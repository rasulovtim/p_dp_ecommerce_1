package com.gitlab.mapper;

import com.gitlab.controller.AbstractIntegrationTest;
import com.gitlab.dto.*;
import com.gitlab.enums.PaymentStatus;
import com.gitlab.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PaymentMapperTest extends AbstractIntegrationTest {

    @Autowired
    private PaymentMapper mapper;

    @Autowired
    private  BankCardMapper bankCardMapper;

    @Test
    void should_map_payment_to_Dto() {

        Payment payment = new Payment();
        BankCard bankCard = new BankCard();
        bankCard.setId(1L);
        bankCard.setCardNumber("4828078439696627");
        bankCard.setDueDate(LocalDate.parse("2029-09-22"));
        bankCard.setSecurityCode(354);

        payment.setBankCard(bankCard);
        payment.setPaymentStatus(PaymentStatus.PAID);
        payment.setCreateDateTime(LocalDateTime.now());
        payment.setOrder(new Order());
        payment.setSum(new BigDecimal(500));
        payment.setUser(new User());

        PaymentDto actualResult = mapper.toDto(payment);

        assertNotNull(actualResult);
        assertEquals(payment.getBankCard(), bankCardMapper.toEntity(actualResult.getBankCardDto()));
        assertEquals(payment.getPaymentStatus(), actualResult.getPaymentStatus());
        assertEquals(payment.getCreateDateTime(), actualResult.getCreateDateTime());
        assertEquals(payment.getOrder().getId(), actualResult.getOrderId());
        assertEquals(payment.getSum(), actualResult.getSum());
        assertEquals(payment.getUser().getId(), actualResult.getUserId());
    }

    @Test
    void should_map_paymentDto_to_Entity() {
        PaymentDto paymentDto = new PaymentDto();

        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setId(1L);
        bankCardDto.setCardNumber("4828078439696627");
        bankCardDto.setDueDate(LocalDate.parse("2029-09-22"));
        bankCardDto.setSecurityCode(354);

        paymentDto.setBankCardDto(bankCardDto);
        paymentDto.setPaymentStatus(PaymentStatus.PAID);
        paymentDto.setCreateDateTime(LocalDateTime.now());
        paymentDto.setOrderId(1L);
        paymentDto.setSum(new BigDecimal(500));
        paymentDto.setUserId(1L);

        Payment actualResult = mapper.toEntity(paymentDto);

        assertNotNull(actualResult);
        assertEquals(paymentDto.getPaymentStatus(), actualResult.getPaymentStatus());
        assertEquals(paymentDto.getCreateDateTime(), actualResult.getCreateDateTime());
        assertEquals(paymentDto.getOrderId(), 1L);
        assertEquals(paymentDto.getSum(), actualResult.getSum());
        assertEquals(paymentDto.getUserId(), 1L);
    }
}