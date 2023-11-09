package com.gitlab.service;

import com.gitlab.client.UserDtoGeneratorClient;
import com.gitlab.dto.*;
import com.gitlab.enums.Gender;
import com.gitlab.model.Passport;
import com.gitlab.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDtoGeneratorService {
    private final UserDtoGeneratorClient userDtoGeneratorClient;

    public UserDto generateUserDto() {
         UserDto userDto = createData();

         userDtoGeneratorClient.create(userDto);
         return userDto;
    }

    public UserDto createData() {
        //TODO добавить рандомные значения к полям
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(1L, "ROLE_USER_JUNIOR")); //TODO проверить, что за id в ROLE

        Set<BankCardDto> bankCardSet = new HashSet<>();
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setCardNumber("0000000000000");
        bankCardDto.setDueDate(LocalDate.of(1900, 1, 1));
        bankCardDto.setSecurityCode(777);
        bankCardSet.add(bankCardDto);

        Set<ShippingAddressDto> personalAddresses = new HashSet<>();
        PersonalAddressDto personalAddressDto = new PersonalAddressDto();
        personalAddressDto.setAddress("address");
        personalAddressDto.setDirections("direction");
        personalAddressDto.setApartment("apartment");
        personalAddressDto.setFloor("floor");
        personalAddressDto.setEntrance("entance");
        personalAddressDto.setDoorCode("doorode");
        personalAddressDto.setPostCode("postode");
        personalAddresses.add(personalAddressDto);

        PassportDto passportDto = new PassportDto();
        passportDto.setCitizenship(Passport.Citizenship.RUSSIA);
        passportDto.setFirstName("user");
        passportDto.setLastName("user");
        passportDto.setBirthDate(LocalDate.of(2000, 5, 15));
        passportDto.setIssueDate(LocalDate.of(2000, 5, 15));
        passportDto.setPassportNumber("09865");
        passportDto.setIssuer("isuer");
        passportDto.setIssuerNumber("issurN");

        UserDto userDto = new UserDto();
        userDto.setEmail("user");
        userDto.setPassword("user");
        userDto.setSecurityQuestion("answer");
        userDto.setSecurityQuestion("queion");
        userDto.setFirstName("user");
        userDto.setLastName("user");
        userDto.setBirthDate(LocalDate.of(1900, 1, 1));
        userDto.setGender(Gender.MALE);
        userDto.setPhoneNumber("890077777");
        userDto.setPassportDto(passportDto);
        userDto.setShippingAddressDtos(personalAddresses);
        userDto.setBankCardDtos(bankCardSet);
        userDto.setRoles(roleSet.stream().map(Role::toString).collect(Collectors.toSet()));


        return userDto;
    }
}
