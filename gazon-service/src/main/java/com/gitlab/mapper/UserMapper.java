package com.gitlab.mapper;

import com.gitlab.dto.UserDto;
import com.gitlab.model.*;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserDto toDto(User user);
//   {
//
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setSecurityQuestion(user.getSecurityQuestion());
//        userDto.setAnswerQuestion(user.getAnswerQuestion());
//        userDto.setFirstName(user.getFirstName());
//        userDto.setLastName(user.getLastName());
//        userDto.setBirthDate(user.getBirthDate());
//        userDto.setGender(user.getGender());
//        userDto.setPhoneNumber(user.getPhoneNumber());
//        userDto.setPassport(user.getPassport());
//        userDto.setCreateDate(LocalDate.now());
//
//        userDto.setShippingAddress(user.getShippingAddress().stream().map(ShippingAddress::getAddress).collect(toSet()));
//        userDto.setBankCards(user.getBankCards().stream().map(BankCard::getCardNumber).collect(toSet()));
//        userDto.setRoles(user.getRoles().stream().map(Role::getName).collect(toSet()));
//
//        return userDto;
//    }

    User toEntity(UserDto userDto);

}

