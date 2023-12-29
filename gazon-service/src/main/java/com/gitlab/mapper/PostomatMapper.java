package com.gitlab.mapper;

import com.gitlab.dto.PostomatDto;
import com.gitlab.model.Postomat;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostomatMapper {

    PostomatDto toDto(Postomat postomat);

    Postomat toEntity(PostomatDto postomatDto);

    default List<PostomatDto> toDtoList(List<Postomat> postomatList) {
        return postomatList.stream().map(this::toDto).collect(Collectors.toList());
    }

    default List<Postomat> toEntityList(List<PostomatDto> postomatDtoList) {
        return postomatDtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
