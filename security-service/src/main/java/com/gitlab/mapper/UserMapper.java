package com.gitlab.mapper;

import com.gitlab.dto.UserDto;
import com.gitlab.model.Role;
import com.gitlab.model.User;
import com.gitlab.service.RoleService;
import com.nimbusds.openid.connect.sdk.claims.Gender;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserMapper {

    @Autowired
    private RoleService roleService;

    @Mapping(source = "rolesSet", target = "roles")
    @Mapping(source = "gender", target = "gender", qualifiedByName = "mapGender")
    public abstract UserDto toDto(User user);

    @Named("mapGender")
    static Gender mapGender(String value) {
        if (Gender.MALE.getValue().equals(value)) {
            return Gender.MALE;
        } else if (Gender.FEMALE.getValue().equals(value)) {
            return Gender.FEMALE;
        } else {
            throw new RuntimeException("mapGender: Gender mapping exception");
        }
    }

    public Set<String> mapStringSetToRoleSet(Set<Role> roleSet) {
        Set<String> stringSet = new HashSet<>();
        if (roleSet == null) {
            return Collections.emptySet();
        }
        for (Role role : roleSet) {
            Optional<String> roleName = role.getName().describeConstable();
            roleName.ifPresent(stringSet::add);
        }
        return stringSet;
    }

    @Mapping(source = "roles", target = "rolesSet")
    public abstract User toEntity(UserDto userDto);

    public Set<Role> mapRoleSetToStringSet(Set<String> stringSet) {
        Set<Role> roleSet = new HashSet<>();
        if (stringSet == null) {
            return Collections.emptySet();
        }
        for (String roleName : stringSet) {
            Optional<Role> roleOptional = roleService.findByName(roleName);
            if (roleOptional.isPresent()) {
                Role role = roleOptional.get();
                roleSet.add(role);
            }
        }
        return roleSet;
    }
}


