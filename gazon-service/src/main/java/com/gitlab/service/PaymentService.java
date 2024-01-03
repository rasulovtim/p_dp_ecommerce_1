package com.gitlab.service;

import com.gitlab.dto.PaymentDto;
import com.gitlab.mapper.PaymentMapper;
import com.gitlab.model.BankCard;
import com.gitlab.model.Order;
import com.gitlab.model.Payment;
import com.gitlab.model.User;
import com.gitlab.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final BankCardService bankCardService;
    private final PaymentMapper paymentMapper;
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

        if (optionalSavedPayment.isEmpty()) {
            return Optional.empty();
        }

        BankCard updatedBankCard = optionalSavedPayment.get().getBankCard();
        Order updatedPaymentOrder = optionalSavedPayment.get().getOrder();
        User updatedPaymentUser = optionalSavedPayment.get().getUser();

        //проверяем, отличаются ли id внутренних сущностей от прешедших в dto, если да - то достаем новые из БД
        if (!updatedBankCard.getId().equals(paymentDto.getBankCardDto().getId())) {
            Optional<BankCard> newPaymentBankCard = bankCardService.findById(paymentDto.getBankCardDto().getId());

            if (newPaymentBankCard.isPresent()) {
                updatedBankCard = newPaymentBankCard.get();
            }
        }

        if (!updatedPaymentOrder.getId().equals(paymentDto.getOrderId())) {
            Optional<Order> newPaymentOrder = orderService.findById(paymentDto.getOrderId());

            if (newPaymentOrder.isPresent()) {
                updatedPaymentOrder = newPaymentOrder.get();
            }
        }

        if (!updatedPaymentUser.getId().equals(paymentDto.getUserId())) {
            Optional<User> newPaymentUser = userService.findUserById(paymentDto.getUserId());

            if (newPaymentUser.isPresent()) {
                updatedPaymentUser = newPaymentUser.get();
            }
        }

        Payment savedPayment = paymentMapper.toUpdateEntity(optionalSavedPayment.get(), paymentDto, updatedBankCard,
                updatedPaymentOrder, updatedPaymentUser);

        savedPayment = paymentRepository.save(savedPayment);

        return Optional.of(paymentMapper.toDto(savedPayment));
    }

    public Optional<Payment> delete(Long id) {
        Optional<Payment> optionalSavedPayment = findById(id);

        if (optionalSavedPayment.isPresent()) {
            paymentRepository.deleteById(id);
        }

        return optionalSavedPayment;
    }
}