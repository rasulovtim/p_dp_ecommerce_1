package com.gitlab.controller;

import com.gitlab.controller.api.PaymentRestApi;
import com.gitlab.dto.PaymentDto;
import com.gitlab.model.Payment;
import com.gitlab.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class PaymentRestController implements PaymentRestApi {

    private final PaymentService paymentService;

    @Override
    public ResponseEntity<List<PaymentDto>> getAll() {
        List<PaymentDto> payment = paymentService.findAllDto();
        if (payment.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(payment);
        }
    }

    @Override
    public ResponseEntity<PaymentDto> get(Long id) {
        return paymentService.findByIdDto(id)
                .map(value -> ResponseEntity.ok(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<PaymentDto> create(PaymentDto paymentDto) {
        PaymentDto savedPaymentDto = paymentService.saveDto(paymentDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedPaymentDto);
    }

    @Override
    public ResponseEntity<PaymentDto> update(Long id, PaymentDto paymentDto) {
        Optional<PaymentDto> updatePaymentDto = paymentService.updateDto(id, paymentDto);
        return updatePaymentDto
                .map(dto -> ResponseEntity.ok(dto))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<PaymentDto> delete(Long id) {
        Optional<Payment> payment = paymentService.delete(id);
        if (payment.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }
}