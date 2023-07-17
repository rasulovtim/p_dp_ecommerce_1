package com.gitlab.controller;

import com.gitlab.dto.UserDto;
import com.gitlab.mapper.UserMapper;
import com.gitlab.model.Role;
import com.gitlab.model.enums.Gender;
import com.gitlab.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserRestControllerIT extends AbstractIntegrationTest{
    private static final String USER_URN = "/api/user";
    private static final String USER_URI = URL + USER_URN;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Test
    void should_get_all_users() throws Exception {
        String expected = objectMapper.writeValueAsString(
                userService
                        .findAll()
                        .stream()
                        .map(userMapper::toDto)
                        .collect(Collectors.toList())
        );

        mockMvc.perform(get(USER_URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_get_user_by_id() throws Exception {
        long id = 1L;
        String expected = objectMapper.writeValueAsString(
                userMapper.toDto(
                        userService
                                .findById(id)
                                .orElse(null))
        );

        mockMvc.perform(get(USER_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_return_not_found_when_get_user_by_non_existent_id() throws Exception {
        long id = 10L;
        mockMvc.perform(get(USER_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_create_user() throws Exception {
        UserDto userDto = generateUser();

        String jsonExampleDto = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post(USER_URI)
                        .content(jsonExampleDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void should_update_user_by_id() throws Exception {
        long id = 1L;
        UserDto userDto = generateUser();

        String jsonExampleDto = objectMapper.writeValueAsString(userDto);

        userDto.setId(id);
        String expected = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(patch(USER_URI + "/{id}", id)
                        .content(jsonExampleDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_return_not_found_when_update_user_by_non_existent_id() throws Exception {
        long id = 10L;
        UserDto userDto = generateUser();

        String jsonExampleDto = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(patch(USER_URI + "/{id}", id)
                        .content(jsonExampleDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_delete_user_by_id() throws Exception {
        long id = 2L;
        mockMvc.perform(delete(USER_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get(USER_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private UserDto generateUser() {
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
        userDto.setCreateDate(LocalDate.now());
        userDto.setRoles(roleSet);

        return userDto;
    }
}
