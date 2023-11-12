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

        @Mapping(source = "bankCard", target = "bankCardDto")
        @Mapping(source = "order.id", target = "orderId")
        @Mapping(source = "user.id", target = "userId")
        public abstract PaymentDto toDto(Payment payment);

        @Mapping(source = "bankCardDto", target = "bankCard")
        @Mapping(source = "orderId", target = "order.id")
        @Mapping(source = "userId", target = "user.id")
        public abstract Payment toEntity(PaymentDto paymentDto);
}


