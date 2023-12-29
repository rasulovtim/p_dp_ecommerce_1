package com.gitlab.mapper;


import com.gitlab.dto.PassportDto;
import com.gitlab.model.Passport;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PassportMapper {

    PassportDto toDto(Passport passport);

    Passport toEntity(PassportDto passportDto);

    default List<PassportDto> toDtoList(List<Passport> passportList) {
        return passportList.stream().map(this::toDto).collect(Collectors.toList());
    }

    default List<Passport> toEntityList(List<PassportDto> passportDtoList) {
        return passportDtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
}