package com.gitlab.mapper;


import com.gitlab.dto.ShippingAddressDto;
import com.gitlab.model.ShippingAddress;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShippingAddressMapper {

    ShippingAddressDto toDto(ShippingAddress shippingAddress);

    ShippingAddress toEntity(ShippingAddressDto shippingAddressDto);


}
