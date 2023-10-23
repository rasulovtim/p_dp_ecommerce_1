package com.gitlab.mapper;

import com.gitlab.dto.BankCardDto;
import com.gitlab.dto.ExampleDto;
import com.gitlab.dto.PaymentDto;
import com.gitlab.dto.ShippingAddressDto;
import com.gitlab.model.*;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PaymentMapperTest {

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
        payment.setPaymentStatus(Payment.PaymentStatus.PAID);
        payment.setCreateDateTime(LocalDateTime.now());
        payment.setSum(new BigDecimal(500));
        payment.setUserId(1L);

        PaymentDto actualResult = mapper.toDto(payment);

        assertNotNull(actualResult);
        assertEquals(payment.getBankCard(), actualResult.getBankCard());
        assertEquals(payment.getShippingAddress(), shippingAddressMapper.toEntity(actualResult.getShippingAddressDto()));
        assertEquals(payment.getShippingDate(), actualResult.getShippingDate());
        assertEquals(payment.getCreateDateTime(), actualResult.getCreateDateTime());
        assertEquals(payment.getSum(), actualResult.getSum());
        assertEquals(payment.getDiscount(), actualResult.getDiscount());
        assertEquals(payment.getBagCounter(), actualResult.getBagCounter());
        assertEquals(payment.getUser().getId(), actualResult.getUserId());
        assertEquals(payment.getOrderStatus(), actualResult.getOrderStatus());
    }






    @Test
    void should_map_exampleDto_to_Entity() {
        ExampleDto exampleDto = new ExampleDto();
        exampleDto.setId(1L);
        exampleDto.setExampleText("text");

        Example actualResult = mapper.toEntity(exampleDto);

        assertNotNull(actualResult);
        assertEquals(exampleDto.getId(), actualResult.getId());
        assertEquals(exampleDto.getExampleText(), actualResult.getExampleText());
    }
}