package com.gitlab.mapper;

import com.gitlab.dto.PaymentDto;
import com.gitlab.model.BankCard;
import com.gitlab.model.Order;
import com.gitlab.model.Payment;
import com.gitlab.model.User;
import org.mapstruct.*;

import java.util.List;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class PaymentMapper {

    @Mapping(source = "bankCard", target = "bankCardDto")
    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "user.id", target = "userId")
    public abstract PaymentDto toDto(Payment payment);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "bankCardDto", target = "bankCard")
    @Mapping(source = "orderId", target = "order.id")
    @Mapping(source = "userId", target = "user.id")
    public abstract Payment toEntity(PaymentDto paymentDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "dto.sum", target = "sum")
    @Mapping(source = "dto.createDateTime", target = "createDateTime")
    @Mapping(source = "order", target = "order")
    @Mapping(source = "user", target = "user")
    public abstract Payment toUpdateEntity(@MappingTarget Payment payment, PaymentDto dto, BankCard bankCard,
                                           Order order, User user);


    public abstract List<PaymentDto> toDtoList(List<Payment> paymentList);

    public abstract List<Payment> toEntityList(List<PaymentDto> paymentDtoList);

}