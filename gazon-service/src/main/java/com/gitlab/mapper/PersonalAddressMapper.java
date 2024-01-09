package com.gitlab.mapper;

import com.gitlab.dto.PersonalAddressDto;
import com.gitlab.model.PersonalAddress;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonalAddressMapper {

    PersonalAddressDto toDto(PersonalAddress personalAddress);

    PersonalAddress toEntity(PersonalAddressDto personalAddressDto);

    List<PersonalAddressDto> toDtoList(List<PersonalAddress> personalAddressList);

    List<PersonalAddress> toEntityList(List<PersonalAddressDto> personalAddressDtoList);
}