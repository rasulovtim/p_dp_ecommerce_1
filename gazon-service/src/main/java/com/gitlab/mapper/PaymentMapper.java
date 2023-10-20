package com.gitlab.mapper;

import com.gitlab.dto.ExampleDto;
import com.gitlab.model.Example;
import com.gitlab.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {

    ExampleDto toDto(Payment payment);

    Payment toEntity(ExampleDto exampleDto);
}