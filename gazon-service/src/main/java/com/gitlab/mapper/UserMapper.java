package com.gitlab.mapper;

import com.gitlab.dto.UserDto;
import com.gitlab.model.*;

import java.util.Collections;

import com.gitlab.service.RoleService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserMapper {

    @Autowired
    private RoleService roleService;

    @Mapping(source = "bankCardsSet", target = "bankCardsDtoSet")
    @Mapping(source = "personalAddressSet", target = "personalAddressDtoSet")
    @Mapping(source = "passport", target = "passportDto")
    @Mapping(source = "rolesSet", target = "roles")
    public abstract UserDto toDto(User user);

    public Set<String> mapStringSetToRoleSet(Set<Role> roleSet) {
        Set<String> stringSet = new HashSet<>();
        if(roleSet==null){
            return Collections.emptySet();
        }
        for (Role role: roleSet) {
            Optional<String> roleName = role.getName().describeConstable();
            roleName.ifPresent(stringSet::add);
        }
        return stringSet;
    }

    @Mapping(source = "bankCardsDtoSet", target = "bankCardsSet")
    @Mapping(source = "personalAddressDtoSet", target = "personalAddressSet")
    @Mapping(source = "passportDto", target = "passport")
    @Mapping(source = "roles", target = "rolesSet")
    public abstract User toEntity(UserDto userDto);

    public Set<Role> mapRoleSetToStringSet(Set<String> stringSet) {
        Set<Role> roleSet = new HashSet<>();
        if(stringSet==null){
            return Collections.emptySet();
        }
        for (String roleName : stringSet) {
            Optional<Role> role = roleService.findByName(roleName);
            role.ifPresent(roleSet::add);
        }
        return roleSet;
    }


}

