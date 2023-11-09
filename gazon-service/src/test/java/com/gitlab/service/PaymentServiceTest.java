package com.gitlab.service;

import com.gitlab.dto.BankCardDto;
import com.gitlab.dto.PaymentDto;
import com.gitlab.model.*;
import com.gitlab.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.gitlab.model.Payment.PaymentStatus.CANCELED;
import static com.gitlab.model.Payment.PaymentStatus.PAID;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;
    @InjectMocks
    private PaymentService paymentService;

    @Test
    void should_find_all_payments() {
        List<Payment> expectedResult = generatePayments();
        when(paymentRepository.findAll()).thenReturn(generatePayments());

        List<Payment> actualResult = paymentService.findAll();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_find_payment_by_id() {
        long id = 1L;
        Payment expectedResult = generatePayment();
        when(paymentRepository.findById(id)).thenReturn(Optional.of(expectedResult));

        Optional<Payment> actualResult = paymentService.findById(id);

        assertEquals(expectedResult, actualResult.orElse(null));
    }

    @Test
    void should_save_payment() {
        Payment expectedResult = generatePayment();
        when(paymentRepository.save(expectedResult)).thenReturn(expectedResult);

        Payment actualResult = paymentService.save(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_update_payment() {
        long id = 1L;
        Payment paymentToUpdate = generatePayment();
        paymentToUpdate.setSum(BigDecimal.valueOf(1000));

        Payment paymentBeforeUpdate = new Payment();
        paymentBeforeUpdate.setId(id);
        paymentBeforeUpdate.setSum(BigDecimal.valueOf(500));

        Payment updatedPayment = new Payment();
        updatedPayment.setId(id);
        updatedPayment.setSum(BigDecimal.valueOf(1000));

        when(paymentRepository.findById(id)).thenReturn(Optional.of(paymentBeforeUpdate));
        when(paymentRepository.save(updatedPayment)).thenReturn(updatedPayment);

        Optional<Payment> actualResult;
        actualResult = paymentService.update(id, paymentToUpdate);

        assertEquals(updatedPayment, actualResult.orElse(null));
    }

    @Test
    void should_not_update_payment_when_entity_not_found() {
        long id = 1L;
        Payment paymentToUpdate = new Payment();
        paymentToUpdate.setSum(BigDecimal.valueOf(1000));

        when(paymentRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Payment> actualResult = paymentService.update(id, paymentToUpdate);

        verify(paymentRepository, never()).save(any());
        assertNull(actualResult.orElse(null));
    }

    @Test
    void should_not_updated_paymentStatus_field_if_null() {
        long id = 1L;
        Payment paymentToUpdate = new Payment();
        paymentToUpdate.setPaymentStatus(null);

        Payment paymentBeforeUpdate = new Payment();
        paymentBeforeUpdate.setPaymentStatus(PAID);

        when(paymentRepository.findById(id)).thenReturn(Optional.of(paymentBeforeUpdate));
        when(paymentRepository.save(paymentBeforeUpdate)).thenReturn(paymentBeforeUpdate);

        Optional<Payment> actualResult = paymentService.update(id, paymentToUpdate);

        verify(paymentRepository).save(paymentBeforeUpdate);
        assertEquals(paymentBeforeUpdate, actualResult.orElse(null));
        assertEquals(PAID, paymentBeforeUpdate.getPaymentStatus());
    }

    @Test
    void should_delete_payment() {
        long id = 1L;
        when(paymentRepository.findById(id)).thenReturn(Optional.of(generatePayment()));

        paymentService.delete(id);

        verify(paymentRepository).deleteById(id);
    }

    @Test
    void should_not_delete_payment_when_entity_not_found() {
        long id = 1L;
        when(paymentRepository.findById(id)).thenReturn(Optional.empty());

        paymentService.delete(id);

        verify(paymentRepository, never()).deleteById(anyLong());
    }

    private List<Payment> generatePayments() {
        return List.of(
                new Payment(1L),
                new Payment(2L),
                new Payment(3L),
                new Payment(4L),
                new Payment(5L),
                new Payment(6L)
        );
    }

    private Payment generatePayment() {
        return new Payment(1L);
    }
}