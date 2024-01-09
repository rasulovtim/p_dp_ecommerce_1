package com.gitlab.mapper;

import com.gitlab.dto.RoleDto;
import com.gitlab.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "roleName", source = "name")
    RoleDto toDto(Role role);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "roleName")
    @Mapping(target = "entityStatus", constant = "ACTIVE")
    Role toEntity(RoleDto roleDto);

    List<RoleDto> toDtoList(List<Role> roleList);

    List<Role> toEntityList(List<RoleDto> roleDtoList);
}