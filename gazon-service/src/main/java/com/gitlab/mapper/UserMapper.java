package com.gitlab.mapper;

import com.gitlab.dto.UserDto;
import com.gitlab.model.*;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mapping(source = "bankCardsSet", target = "bankCardsDtoSet")
    @Mapping(source = "personalAddressSet", target = "personalAddressDtoSet")
    @Mapping(source = "passport", target = "passportDto")
     UserDto toDto(User user);

    default UserDto toDtoRole(User user) {
        UserDto userDto = new UserDto();
        Set<String> roles = user.getRolesSet().stream().map(Role::getName).collect(toSet());
        userDto.setRoles(roles);
        return userDto;
    }

    @Mapping(source = "bankCardsDtoSet", target = "bankCardsSet")
    @Mapping(source = "personalAddressDtoSet", target = "personalAddressSet")
    @Mapping(source = "passportDto", target = "passport")
     User toEntity(UserDto userDto) ;

    default User toEntityRole(UserDto userDto) {
        Set<String> stringSet = userDto.getRoles();
        User user = new User();
        Set<Role> roleSet = stringSet.stream()
                .map(str -> {
                    Role role = new Role();
                    role.setName(str);
                    return role;
                })
                .collect(Collectors.toSet());
        user.setRolesSet(roleSet);
        return user;
    }

}

