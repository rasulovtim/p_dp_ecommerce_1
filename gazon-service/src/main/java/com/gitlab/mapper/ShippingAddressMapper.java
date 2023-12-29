package com.gitlab.mapper;


import com.gitlab.dto.ShippingAddressDto;
import com.gitlab.model.ShippingAddress;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShippingAddressMapper {

    ShippingAddressDto toDto(ShippingAddress shippingAddress);

    ShippingAddress toEntity(ShippingAddressDto shippingAddressDto);

    default List<ShippingAddressDto> toDtoList(List<ShippingAddress> shippingAddressList) {
        return shippingAddressList.stream().map(this::toDto).collect(Collectors.toList());
    }

    default List<ShippingAddress> toEntityList(List<ShippingAddressDto> shippingAddressDtoList) {
        return shippingAddressDtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
