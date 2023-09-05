package com.gitlab.controller;

import com.gitlab.dto.PassportDto;
import com.gitlab.mapper.PassportMapper;
import com.gitlab.model.Passport;
import com.gitlab.service.PassportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDate;
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

class PassportRestControllerTest extends AbstractIntegrationTest {

    private static final String PASSPORT_URN = "/api/passport";
    private static final String PASSPORT_URI = URL + PASSPORT_URN;

    @Autowired
    private PassportService passportService;

    @Autowired
    private PassportMapper passportMapper;

    @Test
    void should_get_all_passports() throws Exception {
        String expected = objectMapper.writeValueAsString(
                passportService
                        .findAll()
                        .stream()
                        .map(passportMapper::toDto)
                        .collect(Collectors.toList())
        );

        mockMvc.perform(get(PASSPORT_URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_get_passport_by_id() throws Exception {
        long id = 1L;
        String expected = objectMapper.writeValueAsString(
                passportMapper.toDto(
                        passportService
                                .findById(id)
                                .orElse(null))
        );

        mockMvc.perform(get(PASSPORT_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_return_not_found_when_get_passport_by_non_existent_id() throws Exception {
        long id = 10L;
        mockMvc.perform(get(PASSPORT_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_create_passport() throws Exception {
        PassportDto passportDto = new PassportDto();

        passportDto.setCitizenship(Passport.Citizenship.RUSSIA);
        passportDto.setFirstName("testFirstName");
        passportDto.setLastName("testLastName");
        passportDto.setPatronym("testPatronym");
        passportDto.setBirthDate(LocalDate.of(2000, 1, 1));
        passportDto.setIssueDate(LocalDate.of(2014, 1, 1));
        passportDto.setPassportNumber("1111 111111");
        passportDto.setIssuer("Test Otdel Police #1");
        passportDto.setIssuerNumber("111-111");

        String jsonPassportDto = objectMapper.writeValueAsString(passportDto);

        mockMvc.perform(post(PASSPORT_URI)
                        .content(jsonPassportDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void should_update_passport_by_id() throws Exception {
        Long id = 1L;
        int numberOfEntitiesExpected = passportService.findAll().size();
        PassportDto passportDto = new PassportDto();

        passportDto.setFirstName("updFirstName");

        passportDto.setCitizenship(Passport.Citizenship.RUSSIA);
        passportDto.setLastName("testLastName");
        passportDto.setPatronym("testPatronym");
        passportDto.setBirthDate(LocalDate.of(1990, 1, 1));
        passportDto.setIssueDate(LocalDate.of(2020, 1, 1));
        passportDto.setPassportNumber("1234 567890");
        passportDto.setIssuer("Test Otedel police №1");
        passportDto.setIssuerNumber("111-111");

        String jsonPassportDto = objectMapper.writeValueAsString(passportDto);

        passportDto.setId(id);
        String expected = objectMapper.writeValueAsString(passportDto);

        mockMvc.perform(patch(PASSPORT_URI + "/{id}", id)
                        .content(jsonPassportDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected))
                .andExpect(result -> assertThat(passportService.findAll().size(),
                        equalTo(numberOfEntitiesExpected)));
    }

    @Test
    void should_return_not_found_when_update_passport_by_non_existent_id() throws Exception {
        long id = 10L;
        PassportDto passportDto = new PassportDto();

        passportDto.setFirstName("updFirstName");

        passportDto.setCitizenship(Passport.Citizenship.RUSSIA);
        passportDto.setLastName("testLastName");
        passportDto.setPatronym("updatedPatronym");
        passportDto.setBirthDate(LocalDate.of(2000, 1, 1));
        passportDto.setIssueDate(LocalDate.of(2014, 1, 1));
        passportDto.setPassportNumber("1111 111111");
        passportDto.setIssuer("Test Otdel Police #1");
        passportDto.setIssuerNumber("111-111");


        String jsonPassportDto = objectMapper.writeValueAsString(passportDto);

        mockMvc.perform(patch(PASSPORT_URI + "/{id}", id)
                        .content(jsonPassportDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_delete_passport_by_id() throws Exception {
        long id = 5L;
        mockMvc.perform(delete(PASSPORT_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get(PASSPORT_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}