package com.gitlab.mapper;

import com.gitlab.dto.PaymentDto;
import com.gitlab.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class PaymentMapper {

        @Mapping(source = "bankCard", target = "bankCardDto")
        @Mapping(source = "order.id", target = "orderId")
        @Mapping(source = "user.id", target = "userId")
        public abstract PaymentDto toDto(Payment payment);

        @Mapping(source = "bankCardDto", target = "bankCard")
        @Mapping(source = "orderId", target = "order.id")
        @Mapping(source = "userId", target = "user.id")
        public abstract Payment toEntity(PaymentDto paymentDto);

    public List<PaymentDto> toDtoList(List<Payment> paymentList) {
        return paymentList.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<Payment> toEntityList(List<PaymentDto> paymentDtoList) {
        return paymentDtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
}


