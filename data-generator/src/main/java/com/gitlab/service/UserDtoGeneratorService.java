package com.gitlab.service;

import com.gitlab.client.UserDtoGeneratorClient;
import com.gitlab.dto.*;
import com.gitlab.enums.Gender;
import com.gitlab.util.DataGenerator;
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
    private final PassportDtoGeneratorService passportDtoGeneratorService;
    private final BankCardDtoGeneratorService bankCardDtoGeneratorService;
    private PersonalAddressDtoGeneratorService personalAddressDtoGeneratorService;

    public UserDto generateUserDto() {
        Set<RoleDto> roleSet = new HashSet<>();
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleName("ROLE_USER_JUNIOR");
        roleSet.add(roleDto);

        Set<BankCardDto> bankCardSet = new HashSet<>();
        bankCardSet.add(bankCardDtoGeneratorService.generateBankCardDto());

        String additionalData = DataGenerator.generateRandomString(3);

        Set<ShippingAddressDto> personalAddresses = new HashSet<>();
        PersonalAddressDto personalAddressDto = personalAddressDtoGeneratorService.generatePersonalAddressDto(additionalData);
        personalAddresses.add(personalAddressDto);

        PassportDto passportDto = passportDtoGeneratorService.generatePassportDtoData(additionalData);

        UserDto userDto = generateUserDtoData(additionalData, passportDto, personalAddresses, bankCardSet, roleSet);;

        userDtoGeneratorClient.create(userDto);
        return userDto;
    }

    public UserDto generateUserDtoData(String additionalData, PassportDto passportDto, Set<ShippingAddressDto> personalAddresses,
                                              Set<BankCardDto> bankCardSet, Set<RoleDto> roleSet) {
        UserDto userDto = new UserDto();
        userDto.setEmail("user" + additionalData);
        userDto.setPassword("user" + additionalData);
        userDto.setSecurityQuestion("answer" + additionalData);
        userDto.setSecurityQuestion("queion" + additionalData);
        userDto.setFirstName("user" + additionalData);
        userDto.setLastName("user" + additionalData);
        userDto.setBirthDate(LocalDate.of(1900, 1, 1));
        userDto.setGender(Gender.MALE);
        userDto.setPhoneNumber("89007777" + additionalData);
        userDto.setPassportDto(passportDto);
        userDto.setShippingAddressDtos(personalAddresses);
        userDto.setBankCardDtos(bankCardSet);
        userDto.setRoles(roleSet.stream().map(RoleDto::toString).collect(Collectors.toSet()));

        return userDto;
    }

}
