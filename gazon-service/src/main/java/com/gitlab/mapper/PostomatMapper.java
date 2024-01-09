package com.gitlab.mapper;

import com.gitlab.dto.PostomatDto;
import com.gitlab.model.Postomat;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostomatMapper {

    PostomatDto toDto(Postomat postomat);

    Postomat toEntity(PostomatDto postomatDto);

    List<PostomatDto> toDtoList(List<Postomat> postomatList);

    List<Postomat> toEntityList(List<PostomatDto> postomatDtoList);
}