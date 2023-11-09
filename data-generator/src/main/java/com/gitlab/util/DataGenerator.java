package com.gitlab.util;

import com.gitlab.dto.*;
import com.gitlab.enums.Gender;
import com.gitlab.model.Passport;
import com.gitlab.model.Role;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class DataGenerator {
    public static String generateRandomSequence(int lengthSequence) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < lengthSequence; i++) {
            int randomNumber = random.nextInt(0, 10);
            stringBuilder.append(randomNumber);
        }

        return stringBuilder.toString();
    }

    public static PassportDto generatePassportDtoData(String additionalData) {
        PassportDto passportDto = new PassportDto();
        passportDto.setCitizenship(Passport.Citizenship.RUSSIA);
        passportDto.setFirstName("user" + additionalData);
        passportDto.setLastName("user" + additionalData);
        passportDto.setBirthDate(LocalDate.of(2000, 5, 15));
        passportDto.setIssueDate(LocalDate.of(2000, 5, 15));

        String passportNumber = DataGenerator.generateRandomSequence(5);
        passportDto.setPassportNumber(passportNumber);

        passportDto.setIssuer("isuer" + additionalData);
        passportDto.setIssuerNumber("issurN" + additionalData);

        return passportDto;
    }

    public static UserDto generateUserDtoData(String additionalData, PassportDto passportDto, Set<ShippingAddressDto> personalAddresses,
                                              Set<BankCardDto> bankCardSet, Set<Role> roleSet) {
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
        userDto.setRoles(roleSet.stream().map(Role::toString).collect(Collectors.toSet()));

        return userDto;
    }

    public static PersonalAddressDto generatePersonalAddressDto(String additionalData) {
        PersonalAddressDto personalAddressDto = new PersonalAddressDto();

        personalAddressDto.setAddress("address" + additionalData);
        personalAddressDto.setDirections("direction" + additionalData);
        personalAddressDto.setApartment("apartment" + additionalData);
        personalAddressDto.setFloor("floor" + additionalData);
        personalAddressDto.setEntrance("entance" + additionalData);
        personalAddressDto.setDoorCode("doorode" + additionalData);
        personalAddressDto.setPostCode("postode" + additionalData);

        return personalAddressDto;
    }

    public static BankCardDto generateBankCardDto() {
        BankCardDto bankCardDto = new BankCardDto();

        String cardNumber = DataGenerator.generateRandomSequence(8);
        bankCardDto.setCardNumber(cardNumber);

        bankCardDto.setDueDate(LocalDate.of(1900, 1, 1));

        int securityCode = Integer.parseInt(DataGenerator.generateRandomSequence(3));
        bankCardDto.setSecurityCode(securityCode);

        return bankCardDto;
    }
}
