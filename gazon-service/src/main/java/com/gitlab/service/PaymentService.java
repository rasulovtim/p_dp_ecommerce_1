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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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

    public Page<Payment> getPage(Integer page, Integer size) {
        if (page == null || size == null) {
            var payments = findAll();
            if (payments.isEmpty()) {
                return Page.empty();
            }
            return new PageImpl<>(payments);
        }
        if (page < 0 || size < 1) {
            return Page.empty();
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        return paymentRepository.findAll(pageRequest);
    }

    public Page<PaymentDto> getPageDto(Integer page, Integer size) {

        if (page == null || size == null) {
            var payments = findAllDto();
            if (payments.isEmpty()) {
                return Page.empty();
            }
            return new PageImpl<>(payments);
        }
        if (page < 0 || size < 1) {
            return Page.empty();
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Payment> paymentPage = paymentRepository.findAll(pageRequest);
        return paymentPage.map(paymentMapper::toDto);
    }

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    public PaymentDto saveDto(PaymentDto paymentDto) {
        Payment payment = paymentMapper.toEntity(paymentDto);
        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toDto(savedPayment);
    }

    public Optional<PaymentDto> updateDto(Long id, PaymentDto paymentDto) {
        Optional<Payment> optionalSavedPayment = findById(id);

        if (optionalSavedPayment.isEmpty()) {
            return Optional.empty();
        }

        Optional<BankCard> paymentBankCard = bankCardService.findById(paymentDto.getBankCardDto().getId());
        if (paymentBankCard.isEmpty()) {
            throw new EntityNotFoundException("Банковская карта не найдена");
        }

        Optional<User> paymentUser = userService.findUserById(paymentDto.getUserId());
        if (paymentUser.isEmpty()) {
            throw new EntityNotFoundException("Пользователь не найден");
        }

        Optional<Order> paymentOrder = orderService.findById(paymentDto.getOrderId());
        if (paymentOrder.isEmpty()) {
            throw new EntityNotFoundException("Заказ не найден");
        }

        Payment savedPayment = paymentMapper.toUpdateEntity(optionalSavedPayment.get(), paymentDto, paymentBankCard.get(),
                paymentOrder.get(), paymentUser.get());

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