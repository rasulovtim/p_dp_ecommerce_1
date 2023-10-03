package com.gitlab.controller;

import com.gitlab.dto.BankCardDto;
import com.gitlab.dto.PassportDto;
import com.gitlab.dto.PersonalAddressDto;
import com.gitlab.dto.ShippingAddressDto;
import com.gitlab.dto.UserDto;
import com.gitlab.mapper.UserMapper;
import com.gitlab.model.Passport;
import com.gitlab.model.User;
import com.gitlab.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.org.hamcrest.CoreMatchers.equalTo;
import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;

class UserRestControllerIT extends AbstractIntegrationTest {

    private static final String USER_URN = "/api/user";
    private static final String USER_URI = URL + USER_URN;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Test
    void should_get_all_users() throws Exception {
        // Ожидаемый JSON-ответ на запрос получения всех пользователей
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
        // ID пользователя, которого мы хотим получить
        long id = 1L;

        // Ожидаемый JSON-ответ на запрос получения пользователя по ID
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
        // ID несуществующего пользователя
        long id = 10L;

        // Ожидаемый статус ответа - "Not Found"
        mockMvc.perform(get(USER_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_create_user() throws Exception {
        // Генерация данных нового пользователя
        UserDto userDto = generateUser();

        // Преобразование объекта пользователя в JSON-строку
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
        // ID пользователя, которого мы хотим обновить
        long id = 1L;

        // Ожидаемое количество сущностей пользователей до обновления
        int numberOfEntitiesExpected = userService.findAll().size();

        // Генерация данных обновленного пользователя
        UserDto userDto = generateUser();

        // Преобразование объекта пользователя в JSON-строку
        String jsonExampleDto = objectMapper.writeValueAsString(userDto);

        // Установка ID обновляемого пользователя
        userDto.setId(id);

        // Ожидаемый JSON-ответ на запрос обновления пользователя
        String expected = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(patch(USER_URI + "/{id}", id)
                        .content(jsonExampleDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected))
                .andExpect(result -> assertThat(userService.findAll().size(),
                        equalTo(numberOfEntitiesExpected)));
    }

    @Test
    void should_return_not_found_when_update_user_by_non_existent_id() throws Exception {
        // ID несуществующего пользователя
        long id = 10L;

        // Генерация данных обновленного пользователя
        UserDto userDto = generateUser();

        // Преобразование объекта пользователя в JSON-строку
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
        // ID пользователя, которого мы хотим удалить
        long id = 1L;

        mockMvc.perform(delete(USER_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());

        // Проверка, что пользователь действительно удален из базы данных
        assertThat(userService.findById(id).isPresent(), equalTo(false));
    }

    @Test
    void should_return_not_found_when_delete_user_by_non_existent_id() throws Exception {
        // ID несуществующего пользователя
        long id = 10L;

        mockMvc.perform(delete(USER_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private UserDto generateUser() {
        Set<String> roleSet = new HashSet<>();
        roleSet.add("ROLE_ADMIN");

        Set<BankCardDto> bankCardSet = new HashSet<>();
        bankCardSet.add(new BankCardDto(
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

        PassportDto passportDto = new PassportDto(
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


        return new UserDto(
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
                passportDto,
                personalAddress,
                bankCardSet,
                roleSet
        );
    }
}