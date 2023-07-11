package com.gitlab.mapper;

import com.gitlab.dto.UserDto;
import com.gitlab.model.*;
import com.gitlab.model.enums.Gender;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testcontainers.shaded.org.hamcrest.Matchers.containsInAnyOrder;

class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void should_map_user_to_Dto() {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(1L, "ROLE_ADMIN"));

        Set<BankCard> bankCardSet = new HashSet<>();
        bankCardSet.add(new BankCard(1L, "0000000000000000", LocalDate.of(1900, 1, 1), 777));

        Set<ShippingAddress> shippingAddressesSet = new HashSet<>();
        shippingAddressesSet.add(new ShippingAddress(1L, "address","directions"));

        User user = new User(1L, "user", "user", "answer", "question", "user", "user", LocalDate.of(1900, 1, 1), Gender.MALE, "89007777777", new Passport(1L, "7777777777"), LocalDate.of(1900, 1, 1), bankCardSet, shippingAddressesSet, roleSet);

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
        assertEquals(user.getPassport(), actualResult.getPassport());

        containsInAnyOrder(user.getShippingAddress(), actualResult.getShippingAddress());
        containsInAnyOrder(user.getBankCards(), actualResult.getBankCards());
        containsInAnyOrder(user.getRoles(), actualResult.getRoles());


    }

    @Test
    void should_map_userDto_to_Entity() {
        Set roleSet = new HashSet<>();
        roleSet.add(new Role(1L, "ROLE_ADMIN"));

        Set bankCardSet = new HashSet<>();
        bankCardSet.add(new BankCard(1L, "0000000000000000", LocalDate.of(1900, 1, 1), 777));

        Set shippingAddressesSet = new HashSet<>();
        shippingAddressesSet.add(new ShippingAddress(1L, "address","directions"));

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("mail@mail.ru");
        userDto.setPassword("user");
        userDto.setSecurityQuestion("answer");
        userDto.setAnswerQuestion("question");
        userDto.setFirstName("user");
        userDto.setLastName("user");
        userDto.setBirthDate(LocalDate.of(1900, 1, 1));
        userDto.setGender(Gender.MALE);
        userDto.setPhoneNumber("89007777777");
        userDto.setPassport(new Passport(1L, "7777777777"));
        userDto.setShippingAddress(shippingAddressesSet);
        userDto.setBankCards(bankCardSet);
        userDto.setRoles(roleSet);


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
        assertEquals(userDto.getPassport(), actualResult.getPassport());

        containsInAnyOrder(userDto.getShippingAddress(), actualResult.getShippingAddress());
        containsInAnyOrder(userDto.getBankCards(), actualResult.getBankCards());
        containsInAnyOrder(userDto.getRoles(), actualResult.getRoles());


    }
}
