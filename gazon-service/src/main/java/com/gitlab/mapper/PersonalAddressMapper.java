package com.gitlab.mapper;

import com.gitlab.dto.PersonalAddressDto;
import com.gitlab.model.PersonalAddress;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonalAddressMapper {
    PersonalAddressDto toDto(PersonalAddress personalAddress);

    PersonalAddress toEntity(PersonalAddressDto personalAddressDto);
}
