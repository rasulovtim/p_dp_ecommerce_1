package com.gitlab.mapper;

import com.gitlab.dto.PassportDto;
import com.gitlab.model.Passport;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PassportMapper {

    PassportDto toDto(Passport passport);

    Passport toEntity(PassportDto passportDto);

    List<PassportDto> toDtoList(List<Passport> passportList);

    List<Passport> toEntityList(List<PassportDto> passportDtoList);
}