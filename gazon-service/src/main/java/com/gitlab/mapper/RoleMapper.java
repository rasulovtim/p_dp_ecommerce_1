package com.gitlab.mapper;


import com.gitlab.dto.RoleDto;
import com.gitlab.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "roleName", source = "name")
    RoleDto toDto(Role role);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "roleName")
    @Mapping(target = "entityStatus", constant = "ACTIVE")
    Role toEntity(RoleDto roleDto);

    default List<RoleDto> toDtoList(List<Role> roleList) {
        return roleList.stream().map(this::toDto).collect(Collectors.toList());
    }

    default List<Role> toEntityList(List<RoleDto> roleDtoList) {
        return roleDtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
}