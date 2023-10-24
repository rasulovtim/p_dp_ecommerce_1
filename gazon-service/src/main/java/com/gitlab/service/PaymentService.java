package com.gitlab.service;

import com.gitlab.dto.PaymentDto;
import com.gitlab.mapper.BankCardMapper;
import com.gitlab.mapper.PaymentMapper;
import com.gitlab.model.Payment;
import com.gitlab.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final PaymentMapper paymentMapper;

    private final BankCardMapper bankCardMapper;

    private final OrderService orderService;

    private final UserService userService;

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public List<PaymentDto> findAllDto() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream()
                .map(paymentMapper::toDto)
                .collect(Collectors.toList());
    }

    public Page<Payment> getPage(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return paymentRepository.findAll(pageRequest);
    }

    public Page<PaymentDto> getPageDto(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Payment> paymentPage = paymentRepository.findAll(pageRequest);
        return paymentPage.map(paymentMapper::toDto);
    }

    public Optional<Payment> findById(Long id) {
        return paymentRepository.findById(id);
    }

    public Optional<PaymentDto> findByIdDto(Long id) {
        return paymentRepository.findById(id)
                .map(paymentMapper::toDto);
    }

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    public PaymentDto saveDto(PaymentDto paymentDto) {
        Payment payment = paymentMapper.toEntity(paymentDto);
        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toDto(savedPayment);
    }

    public Optional<Payment> update(Long id, Payment payment) {
        Optional<Payment> optionalSavedPayment = findById(id);
        Payment savedPayment;

        if (optionalSavedPayment.isEmpty()) {
            return optionalSavedPayment;
        } else {
            savedPayment = optionalSavedPayment.get();
        }
        if (payment.getBankCard() != null) {
            savedPayment.setBankCard(payment.getBankCard());
        }
        if (payment.getPaymentStatus() != null) {
            savedPayment.setPaymentStatus(payment.getPaymentStatus());
        }
        if (payment.getCreateDateTime() != null) {
            savedPayment.setCreateDateTime(payment.getCreateDateTime());
        }
        if (payment.getOrder() != null) {
            savedPayment.setOrder(payment.getOrder());
        }
        if (payment.getSum() != null) {
            savedPayment.setSum(payment.getSum());
        }
        if (payment.getUser() != null) {
            savedPayment.setUser(payment.getUser());
        }

        return Optional.of(paymentRepository.save(savedPayment));
    }

    public Optional<PaymentDto> updateDto(Long id, PaymentDto paymentDto) {
        Optional<Payment> optionalSavedPayment = findById(id);
        Payment savedPayment;

        if (optionalSavedPayment.isEmpty()) {
            return Optional.empty();
        } else {
            savedPayment = optionalSavedPayment.get();
        }
        if (paymentDto.getBankCardDto() != null) {
            savedPayment.setBankCard(bankCardMapper.toEntity(paymentDto.getBankCardDto()));
        }
        if (paymentDto.getPaymentStatus() != null) {
            savedPayment.setPaymentStatus(paymentDto.getPaymentStatus());
        }
        if (paymentDto.getCreateDateTime() != null) {
            savedPayment.setCreateDateTime(paymentDto.getCreateDateTime());
        }
        if (paymentDto.getOrderId() != null) {
            savedPayment.setOrder(orderService.findById(paymentDto.getOrderId()).get());
        }
        if (paymentDto.getSum() != null) {
            savedPayment.setSum(paymentDto.getSum());
        }
        if (paymentDto.getUserId() != null) {
            savedPayment.setUser(userService.findById(paymentDto.getUserId()).get());
        }

        savedPayment = paymentRepository.save(savedPayment);
        return Optional.of(paymentMapper.toDto(savedPayment));
    }

    public Optional<Payment> delete(Long id) {
        Optional<Payment> optionalSavedPayment = findById(id);
        if (optionalSavedPayment.isEmpty()) {
            return optionalSavedPayment;
        } else {
            paymentRepository.deleteById(id);
            return optionalSavedPayment;
        }
    }
}