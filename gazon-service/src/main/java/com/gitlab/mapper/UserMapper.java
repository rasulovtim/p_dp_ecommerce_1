package com.gitlab.mapper;

import com.gitlab.dto.*;
import com.gitlab.model.*;

import java.util.Collections;

import com.gitlab.service.RoleService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserMapper {

    @Autowired
    private RoleService roleService;

    @Mapping(source = "bankCardsSet", target = "bankCardDtos")
    @Mapping(source = "shippingAddressSet", target = "shippingAddressDtos")
    @Mapping(source = "passport", target = "passportDto")
    @Mapping(source = "rolesSet", target = "roles")
    public abstract UserDto toDto(User user);

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

    @Mapping(source = "bankCardDtos", target = "bankCardsSet")
    @Mapping(source = "shippingAddressDtos", target = "shippingAddressSet")
    @Mapping(source = "passportDto", target = "passport")
    @Mapping(source = "roles", target = "rolesSet")
    public abstract User toEntity(UserDto userDto);

    public Set<Role> mapRoleSetToStringSet(Set<String> stringSet) {
        Set<Role> roleSet = new HashSet<>();
        if (stringSet == null) {
            return Collections.emptySet();
        }
        for (String roleName : stringSet) {
            Optional<Role> role = roleService.findByName(roleName);
            role.ifPresent(roleSet::add);
        }
        return roleSet;
    }

    @Named("createShippingAddress")
    public ShippingAddress createShippingAddress(ShippingAddressDto dto) {
        if (dto.getClass() == PersonalAddressDto.class) {
            return createPersonalAddressEntity((PersonalAddressDto) dto);
        } else if (dto.getClass() == PostomatDto.class) {
            return createPostomatEntity((PostomatDto) dto);
        } else if (dto.getClass() == PickupPointDto.class) {
            return createPickupPointEntity((PickupPointDto) dto);
        } else {
            throw new IllegalArgumentException("Unknown ShippingAddressDto subtype");
        }
    }

    @Named("createShippingAddressDto")
    public ShippingAddressDto createShippingAddressDto(ShippingAddress shippingAddress) {
        if (shippingAddress.getClass() == PersonalAddress.class) {
            return createPersonalAddressDto((PersonalAddress) shippingAddress);
        } else if (shippingAddress.getClass() == Postomat.class) {
            return createPostomatDto((Postomat) shippingAddress);
        } else if (shippingAddress.getClass() == PickupPoint.class) {
            return createPickupPointDto((PickupPoint) shippingAddress);
        } else {
            throw new IllegalArgumentException("Unknown ShippingAddress subtype");
        }
    }

    public abstract PersonalAddressDto createPersonalAddressDto(PersonalAddress personalAddress);

    public abstract PostomatDto createPostomatDto(Postomat postomat);

    public abstract  PickupPointDto createPickupPointDto(PickupPoint pickupPoint);


    public abstract PersonalAddress createPersonalAddressEntity(PersonalAddressDto personalAddressDto);

    public abstract Postomat createPostomatEntity(PostomatDto postomatDto);

    public abstract  PickupPoint createPickupPointEntity(PickupPointDto pickupPointDto);


    public Set<ShippingAddressDto> mapShippingAddressEntityToShippingAddressDto(Set<ShippingAddress> shippingAddress) {
        return shippingAddress.stream()
                .map(this::createShippingAddressDto)
                .collect(Collectors.toSet());
    }

    public Set<ShippingAddress> mapShippingAddressDtoToShippingAddressSetEntity(Set<ShippingAddressDto> shippingAddressDtoSet) {
        return shippingAddressDtoSet.stream()
                .map(this::createShippingAddress)
                .collect(Collectors.toSet());
    }

}


