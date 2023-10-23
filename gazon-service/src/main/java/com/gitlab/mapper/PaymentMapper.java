package com.gitlab.mapper;

import com.gitlab.dto.OrderDto;
import com.gitlab.dto.PaymentDto;
import com.gitlab.model.*;
import com.gitlab.repository.PaymentRepository;
import com.gitlab.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class PaymentMapper {

//    private PaymentRepository paymentRepository;
//
//    @Mapping(source = "somePayment", target = "paymentId")
//    public abstract PaymentDto toDto(Payment payment);
//
//    public Long mapPaymentToPaymentId(Payment payment) {
//        if (payment == null) {
//            return null;
//        }
//        return payment.getId();
//    }
//
//    @Mapping(source = "paymentId", target = "somePayment")
//    public abstract Payment toEntity(PaymentDto paymentDto);
//
//    public Payment mapPaymentIdToPayment(Long paymentId) {
//        if (paymentId == null) {
//            return null;
//        }
//        return paymentRepository.findById(paymentId).orElse(null);
//    }
//        @Autowired
//        protected BankCard bankCard;

        @Autowired
        protected UserService userService;

        @Mapping(source = "user.id", target = "userId")
        @Mapping(source = "bankCard", target = "bankCardDto")
        public abstract PaymentDto toDto(Payment payment);

        public User mapUserIdToUser(Long userId) {
            if (userId == null) {
                return null;
            }
            return userService.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User wasn't found"));
        }

        @Mapping(source = "userId", target = "user.id")
        @Mapping(source = "BankCardDto", target = "bankCard")
        public abstract BankCard toEntity(PaymentDto paymentDto);
}

