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
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(1L, "ROLE_ADMIN"));

        Set<BankCardDto> bankCardSet = new HashSet<>();
        bankCardSet.add(new BankCardDto(1L, "0000000000000", LocalDate.of(1900, 1, 1), 777));

        Set<ShippingAddressDto> personalAddresses = new HashSet<>();
        personalAddresses.add(new PersonalAddressDto(1L,
                "address",
                "direction",
                "apartment",
                "floor",
                "entance",
                "doorode",
                "postode"));

        PassportDto passportDto = new PassportDto(
                1L,
                Passport.Citizenship.RUSSIA,
                "user",
                "user",
                "paonym",
                LocalDate.of(2000, 5, 15),
                LocalDate.of(2000, 5, 15),
                "09865",
                "isuer",
                "issurN");

         UserDto userDto = new UserDto(1L,
                "user",
                "user",
                "anwer",
                "queion",
                "user",
                "user",
                LocalDate.of(1900, 1, 1),
                Gender.MALE,
                "890077777",
                passportDto,
                personalAddresses,
                bankCardSet,
                roleSet.stream().map(Role::toString).collect(Collectors.toSet()));

         userDtoGeneratorClient.create(userDto);
         return userDto;
    }
}
