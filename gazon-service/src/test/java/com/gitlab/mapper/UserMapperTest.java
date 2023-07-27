package com.gitlab.mapper;

import com.gitlab.controller.AbstractIntegrationTest;
import com.gitlab.dto.*;
import com.gitlab.model.*;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testcontainers.shaded.org.hamcrest.Matchers.containsInAnyOrder;

class UserMapperTest extends AbstractIntegrationTest {

    @Autowired
    private UserMapper mapper;

    @Test
    void should_map_user_to_Dto() {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(1L, "ROLE_ADMIN"));

        Set<BankCard> bankCardSet = new HashSet<>();
        bankCardSet.add(new BankCard(1L, "0000000000000000", LocalDate.now(), 777));

        Set<ShippingAddress> personalAddresses = new HashSet<>();
        personalAddresses.add(new PersonalAddress(
                "apartment",
                "floor",
                "entrance",
                "doorCode",
                "postCode"));

        Passport passport = new Passport(
                1L,
                Passport.Citizenship.RUSSIA,
                "user",
                "user",
                "patronym",
                LocalDate.now(),
                LocalDate.now(),
                "098765",
                "issuer",
                "issuerN");

        User user = new User(1L,
                "user",
                "user",
                "answer",
                "question",
                "user",
                "user",
                LocalDate.now(),
                User.Gender.MALE,
                "89007777777",
                passport,
                LocalDate.now(),
                bankCardSet,
                personalAddresses,
                roleSet);

        UserDto actualResult = mapper.toDto(user);

        assertNotNull(actualResult);
        assertEquals(user.getId(), actualResult.getId());
        assertEquals(user.getEmail(), actualResult.getEmail());
        assertEquals(user.getPassword(), actualResult.getPassword());
        assertEquals(user.getSecurityQuestion(), actualResult.getSecurityQuestion());
        assertEquals(user.getAnswerQuestion(), actualResult.getAnswerQuestion());
        assertEquals(user.getFirstName(), actualResult.getFirstName());
        assertEquals(user.getLastName(), actualResult.getLastName());
        assertEquals(user.getBirthDate(), actualResult.getBirthDate());
        assertEquals(user.getGender(), actualResult.getGender());
        assertEquals(user.getPhoneNumber(), actualResult.getPhoneNumber());
        assertEquals(user.getCreateDate(), LocalDate.now());

        //Test all collections of fields PersonalAddress & PersonalAddressDto
//        assertEquals(user.getPersonalAddressSet().stream().map(PersonalAddress::getApartment).collect(Collectors.toSet()), actualResult.getPersonalAddressDtoSet().stream().map(PersonalAddressDto::getApartment).collect(Collectors.toSet()));
//        assertEquals(user.getPersonalAddressSet().stream().map(PersonalAddress::getFloor).collect(Collectors.toSet()), actualResult.getPersonalAddressDtoSet().stream().map(PersonalAddressDto::getFloor).collect(Collectors.toSet()));
//        assertEquals(user.getPersonalAddressSet().stream().map(PersonalAddress::getEntrance).collect(Collectors.toSet()), actualResult.getPersonalAddressDtoSet().stream().map(PersonalAddressDto::getEntrance).collect(Collectors.toSet()));
//        assertEquals(user.getPersonalAddressSet().stream().map(PersonalAddress::getDoorCode).collect(Collectors.toSet()), actualResult.getPersonalAddressDtoSet().stream().map(PersonalAddressDto::getDoorCode).collect(Collectors.toSet()));
//        assertEquals(user.getPersonalAddressSet().stream().map(PersonalAddress::getPostCode).collect(Collectors.toSet()), actualResult.getPersonalAddressDtoSet().stream().map(PersonalAddressDto::getPostCode).collect(Collectors.toSet()));

        //Test all collections of fields BankCard & BankCardDto
        assertEquals(user.getBankCardsSet().stream().map(BankCard::getCardNumber).collect(Collectors.toSet()), actualResult.getBankCardsDtoSet().stream().map(BankCardDto::getCardNumber).collect(Collectors.toSet()));
        assertEquals(user.getBankCardsSet().stream().map(BankCard::getDueDate).collect(Collectors.toSet()), actualResult.getBankCardsDtoSet().stream().map(BankCardDto::getDueDate).collect(Collectors.toSet()));
        assertEquals(user.getBankCardsSet().stream().map(BankCard::getSecurityCode).collect(Collectors.toSet()), actualResult.getBankCardsDtoSet().stream().map(BankCardDto::getSecurityCode).collect(Collectors.toSet()));

        //Test all collections of field Role & String
        assertEquals(user.getRolesSet().stream().map(Role::getName).collect(Collectors.toSet()), actualResult.getRoles().stream().flatMap(String::lines).collect(Collectors.toSet()));

        //test all field custom object Passport & PassportDto
        assertEquals(user.getPassport().getCitizenship(), actualResult.getPassportDto().getCitizenship());
        assertEquals(user.getPassport().getId(), actualResult.getPassportDto().getId());
        assertEquals(user.getPassport().getFirstName(), actualResult.getPassportDto().getFirstName());
        assertEquals(user.getPassport().getLastName(), actualResult.getPassportDto().getLastName());
        assertEquals(user.getPassport().getPatronym(), actualResult.getPassportDto().getPatronym());
        assertEquals(user.getPassport().getBirthDate(), actualResult.getPassportDto().getBirthDate());
        assertEquals(user.getPassport().getIssueDate(), actualResult.getPassportDto().getIssueDate());
        assertEquals(user.getPassport().getPassportNumber(), actualResult.getPassportDto().getPassportNumber());
        assertEquals(user.getPassport().getIssuer(), actualResult.getPassportDto().getIssuer());
        assertEquals(user.getPassport().getIssuerNumber(), actualResult.getPassportDto().getIssuerNumber());

    }

    @Test
    void should_map_userDto_to_Entity() {
        Set<String> roleSet = new HashSet<>();
        roleSet.add("ROLE_ADMIN");

        Set<BankCardDto> bankCard = new HashSet<>();
        bankCard.add(new BankCardDto(
                1L,
                "1111222233334444",
                LocalDate.now(),
                423
        ));

        Set<ShippingAddressDto> personalAddress = new HashSet<>();
        personalAddress.add(new PersonalAddressDto(
                1L,
                "address",
                "directions",
                "apartment",
                "floor",
                "entrance",
                "doorCode",
                "postCode"));

        PassportDto passport = new PassportDto(
                1L,
                Passport.Citizenship.RUSSIA,
                "user",
                "user",
                "patronym",
                LocalDate.now(),
                LocalDate.now(),
                "098765",
                "issuer",
                "issuerN");

        UserDto userDto = new UserDto(
                1L,
                "mail@mail.ru",
                "user",
                "answer",
                "question",
                "user",
                "user",
                LocalDate.now(),
                User.Gender.MALE,
                "89007777777",
                passport,
                personalAddress,
                bankCard,
                roleSet);

        User actualResult = mapper.toEntity(userDto);

        assertNotNull(actualResult);
        assertEquals(userDto.getId(), actualResult.getId());
        assertEquals(userDto.getEmail(), actualResult.getEmail());
        assertEquals(userDto.getPassword(), actualResult.getPassword());
        assertEquals(userDto.getSecurityQuestion(), actualResult.getSecurityQuestion());
        assertEquals(userDto.getAnswerQuestion(), actualResult.getAnswerQuestion());
        assertEquals(userDto.getFirstName(), actualResult.getFirstName());
        assertEquals(userDto.getLastName(), actualResult.getLastName());
        assertEquals(userDto.getBirthDate(), actualResult.getBirthDate());
        assertEquals(userDto.getGender(), actualResult.getGender());
        assertEquals(userDto.getPhoneNumber(), actualResult.getPhoneNumber());

        //test all field custom object Passport & PassportDto
        assertEquals(userDto.getPassportDto().getCitizenship(), actualResult.getPassport().getCitizenship());
        assertEquals(userDto.getPassportDto().getId(), actualResult.getPassport().getId());
        assertEquals(userDto.getPassportDto().getFirstName(), actualResult.getPassport().getFirstName());
        assertEquals(userDto.getPassportDto().getLastName(), actualResult.getPassport().getLastName());
        assertEquals(userDto.getPassportDto().getPatronym(), actualResult.getPassport().getPatronym());
        assertEquals(userDto.getPassportDto().getBirthDate(), actualResult.getPassport().getBirthDate());
        assertEquals(userDto.getPassportDto().getIssueDate(), actualResult.getPassport().getIssueDate());
        assertEquals(userDto.getPassportDto().getPassportNumber(), actualResult.getPassport().getPassportNumber());
        assertEquals(userDto.getPassportDto().getIssuer(), actualResult.getPassport().getIssuer());
        assertEquals(userDto.getPassportDto().getIssuerNumber(), actualResult.getPassport().getIssuerNumber());

        //Test all collections of fields PersonalAddress & PersonalAddressDto
//        assertEquals(userDto.getPersonalAddressDtoSet().stream().map(PersonalAddressDto::getApartment).collect(Collectors.toSet()), actualResult.getPersonalAddressSet().stream().map(PersonalAddress::getApartment).collect(Collectors.toSet()));
//        assertEquals(userDto.getPersonalAddressDtoSet().stream().map(PersonalAddressDto::getFloor).collect(Collectors.toSet()), actualResult.getPersonalAddressSet().stream().map(PersonalAddress::getFloor).collect(Collectors.toSet()));
//        assertEquals(userDto.getPersonalAddressDtoSet().stream().map(PersonalAddressDto::getEntrance).collect(Collectors.toSet()), actualResult.getPersonalAddressSet().stream().map(PersonalAddress::getEntrance).collect(Collectors.toSet()));
//        assertEquals(userDto.getPersonalAddressDtoSet().stream().map(PersonalAddressDto::getDoorCode).collect(Collectors.toSet()), actualResult.getPersonalAddressSet().stream().map(PersonalAddress::getDoorCode).collect(Collectors.toSet()));
//        assertEquals(userDto.getPersonalAddressDtoSet().stream().map(PersonalAddressDto::getPostCode).collect(Collectors.toSet()), actualResult.getPersonalAddressSet().stream().map(PersonalAddress::getPostCode).collect(Collectors.toSet()));
        //Test all collections of fields BankCard & BankCardDto
        assertEquals(userDto.getBankCardsDtoSet().stream().map(BankCardDto::getCardNumber).collect(Collectors.toSet()), actualResult.getBankCardsSet().stream().map(BankCard::getCardNumber).collect(Collectors.toSet()));
        assertEquals(userDto.getBankCardsDtoSet().stream().map(BankCardDto::getDueDate).collect(Collectors.toSet()), actualResult.getBankCardsSet().stream().map(BankCard::getDueDate).collect(Collectors.toSet()));
        assertEquals(userDto.getBankCardsDtoSet().stream().map(BankCardDto::getSecurityCode).collect(Collectors.toSet()), actualResult.getBankCardsSet().stream().map(BankCard::getSecurityCode).collect(Collectors.toSet()));
        //Test all collections of fields Role & String
        assertEquals(userDto.getRoles().stream().flatMap(String::lines).collect(Collectors.toSet()), actualResult.getRolesSet().stream().map(Role::getName).collect(Collectors.toSet()));

    }
}
