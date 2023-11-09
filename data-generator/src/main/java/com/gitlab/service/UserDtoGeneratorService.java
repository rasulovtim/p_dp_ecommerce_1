package com.gitlab.service;

import com.gitlab.client.UserDtoGeneratorClient;
import com.gitlab.dto.*;
import com.gitlab.model.Role;
import com.gitlab.util.DataGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDtoGeneratorService {
    private final UserDtoGeneratorClient userDtoGeneratorClient;

    public UserDto generateUserDto() {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(4L, "ROLE_USER_JUNIOR"));

        Set<BankCardDto> bankCardSet = new HashSet<>();
        bankCardSet.add(DataGenerator.generateBankCardDto());

        String additionalData = DataGenerator.generateRandomSequence(3);

        Set<ShippingAddressDto> personalAddresses = new HashSet<>();
        PersonalAddressDto personalAddressDto = DataGenerator.generatePersonalAddressDto(additionalData);
        personalAddresses.add(personalAddressDto);

        PassportDto passportDto = DataGenerator.generatePassportDtoData(additionalData);

        UserDto userDto = DataGenerator.generateUserDtoData(additionalData, passportDto, personalAddresses, bankCardSet, roleSet);;

        userDtoGeneratorClient.create(userDto);
        return userDto;
    }

}
