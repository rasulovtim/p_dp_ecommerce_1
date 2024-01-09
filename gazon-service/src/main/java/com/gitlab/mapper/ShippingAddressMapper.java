package com.gitlab.mapper;

import com.gitlab.dto.ShippingAddressDto;
import com.gitlab.model.ShippingAddress;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShippingAddressMapper {

    ShippingAddressDto toDto(ShippingAddress shippingAddress);

    ShippingAddress toEntity(ShippingAddressDto shippingAddressDto);

    List<ShippingAddressDto> toDtoList(List<ShippingAddress> shippingAddressList);

    List<ShippingAddress> toEntityList(List<ShippingAddressDto> shippingAddressDtoList);
}