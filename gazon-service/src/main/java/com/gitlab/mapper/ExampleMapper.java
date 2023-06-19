package com.gitlab.mapper;

import com.gitlab.dto.ExampleDto;
import com.gitlab.model.Example;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExampleMapper {
    ExampleDto toDto(Example example);

    Example toEntity(ExampleDto exampleDto);
}
