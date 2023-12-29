package com.gitlab.mapper;

import com.gitlab.dto.PersonalAddressDto;
import com.gitlab.model.PersonalAddress;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonalAddressMapper {

    PersonalAddressDto toDto(PersonalAddress personalAddress);

    PersonalAddress toEntity(PersonalAddressDto personalAddressDto);

    default List<PersonalAddressDto> toDtoList(List<PersonalAddress> personalAddressList) {
        return personalAddressList.stream().map(this::toDto).collect(Collectors.toList());
    }

    default List<PersonalAddress> toEntityList(List<PersonalAddressDto> personalAddressDtoList) {
        return personalAddressDtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
