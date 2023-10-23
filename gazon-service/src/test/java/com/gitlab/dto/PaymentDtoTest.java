package com.gitlab.dto;

import com.gitlab.model.Payment;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentDtoTest extends AbstractDtoTest {

    @Test
    void test_valid_payment() {
        var paymentDto = generatePaymentDto();
        assertTrue(validator.validate(paymentDto).isEmpty());
    }

    @Test
    void test_invalid_bank_card(){
        var paymentDto = generatePaymentDto();
        paymentDto.setBankCardDto(null);

        assertFalse(validator.validate(paymentDto).isEmpty());
    }

    @Test
    void test_invalid_payment_status(){
        var paymentDto = generatePaymentDto();
        paymentDto.setPaymentStatus(null);

        assertFalse(validator.validate(paymentDto).isEmpty());
    }

    @Test
    void test_invalid_create_date_time(){
        var paymentDto = generatePaymentDto();
        paymentDto.setCreateDateTime(null);

        assertFalse(validator.validate(paymentDto).isEmpty());
    }

    @Test
    void test_invalid_orderId(){
        var paymentDto = generatePaymentDto();
        paymentDto.setOrderId(null);

        assertFalse(validator.validate(paymentDto).isEmpty());
    }

    @Test
    void test_invalid_sum(){
        var paymentDto = generatePaymentDto();
        paymentDto.setSum(null);

        assertFalse(validator.validate(paymentDto).isEmpty());
    }


    @Test
    void test_invalid_userId(){
        var paymentDto = generatePaymentDto();
        paymentDto.setUserId(null);

        assertFalse(validator.validate(paymentDto).isEmpty());
    }

    private PaymentDto generatePaymentDto() {
        PaymentDto paymentDto = new PaymentDto();
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setId(1L);

        paymentDto.setBankCardDto(bankCardDto);
        paymentDto.setPaymentStatus(Payment.PaymentStatus.PAID);
        paymentDto.setCreateDateTime(LocalDateTime.now());

        paymentDto.setOrderId(1L);
        paymentDto.setSum(new BigDecimal(500));
        paymentDto.setUserId(1L);
        return paymentDto;
    }

}
