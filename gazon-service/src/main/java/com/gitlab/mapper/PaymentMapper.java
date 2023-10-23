package com.gitlab.mapper;

import com.gitlab.dto.PaymentDto;
import com.gitlab.model.*;
import com.gitlab.service.OrderService;
import com.gitlab.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class PaymentMapper {
    protected UserService userService;
    protected OrderService orderService;
        @Mapping(source = "user.id", target = "userId")
        @Mapping(source = "bankCard", target = "bankCardDto")
        @Mapping(source = "order", target = "orderId")
        public abstract PaymentDto toDto(Payment payment);

        public User mapUserIdToUser(Long userId) {
            if (userId == null) {
                return null;
            }
            return userService.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User wasn't found"));
        }

        public Order mapOrderIdToUser(Long orderId) {
        if (orderId == null) {
            return null;
        }
        return orderService.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order wasn't found"));
    }
        @Mapping(source = "userId", target = "user.id")
        @Mapping(source = "bankCardDto", target = "bankCard")
        @Mapping(source = "orderId", target = "order.id")
        public abstract Payment toEntity(PaymentDto paymentDto);
}


