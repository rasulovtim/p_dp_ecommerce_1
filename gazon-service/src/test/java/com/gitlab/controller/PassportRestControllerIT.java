package com.gitlab.controller;

import com.gitlab.dto.PassportDto;
import com.gitlab.enums.Citizenship;
import com.gitlab.mapper.PassportMapper;
import com.gitlab.service.PassportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.org.hamcrest.CoreMatchers.equalTo;
import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;

class PassportRestControllerIT extends AbstractIntegrationTest {

    private static final String PASSPORT_URN = "/api/passport";
    private static final String PASSPORT_URI = URL + PASSPORT_URN;

    @Autowired
    private PassportService passportService;

    @Autowired
    private PassportMapper passportMapper;

    @Test
    @Transactional(readOnly = true)
    void should_get_all_passports() throws Exception {

        var response = passportService.getPage(null, null);
        var expected = objectMapper.writeValueAsString(passportMapper.toDtoList(response.getContent()));

        mockMvc.perform(get(PASSPORT_URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    @Transactional(readOnly = true)
    void should_get_page() throws Exception {
        int page = 0;
        int size = 2;
        String parameters = "?page=" + page + "&size=" + size;

        var response = passportService.getPage(page, size);
        assertFalse(response.getContent().isEmpty());

        var expected = objectMapper.writeValueAsString(passportMapper.toDtoList(response.getContent()));

        mockMvc.perform(get(PASSPORT_URI + parameters))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_get_page_with_incorrect_parameters() throws Exception {
        int page = 0;
        int size = -2;
        String parameters = "?page=" + page + "&size=" + size;

        mockMvc.perform(get(PASSPORT_URI + parameters))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void should_get_page_without_content() throws Exception {
        int page = 10;
        int size = 100;
        String parameters = "?page=" + page + "&size=" + size;

        mockMvc.perform(get(PASSPORT_URI + parameters))
                .andDo(print())
                .andExpect(status().isNoContent());
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
        PassportDto passportDto = generatePassportDto();

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
        PassportDto passportDto = generatePassportDto();
        PassportDto saved = passportService.saveDto(passportDto);

        int numberOfEntitiesExpected = passportService.findAllActive().size();

        PassportDto updated = generatePassportDto();
        updated.setId(saved.getId());

        String jsonPassportDto = objectMapper.writeValueAsString(updated);

        String expected = objectMapper.writeValueAsString(updated);

        mockMvc.perform(patch(PASSPORT_URI + "/{id}", saved.getId())
                        .content(jsonPassportDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected))
                .andExpect(result -> assertThat(passportService.findAllActive().size(),
                        equalTo(numberOfEntitiesExpected)));
    }

    @Test
    void should_return_not_found_when_update_passport_by_non_existent_id() throws Exception {
        long id = 1000000L;

        PassportDto passportDto = generatePassportDto();

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
        PassportDto passportDto = generatePassportDto();

        PassportDto saved = passportService.saveDto(passportDto);

        mockMvc.perform(delete(PASSPORT_URI + "/{id}", saved.getId()))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get(PASSPORT_URI + "/{id}", saved.getId()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private PassportDto generatePassportDto() {
        PassportDto passportDto = new PassportDto();
        passportDto.setId(10L);
        passportDto.setCitizenship(Citizenship.RUSSIA);
        passportDto.setFirstName("Ivan");
        passportDto.setLastName("Petrov");
        passportDto.setPatronym("Aleksandrovich");
        passportDto.setBirthDate(LocalDate.of(2000, 5, 20));
        passportDto.setIssueDate(LocalDate.of(2014, 6, 10));
        passportDto.setPassportNumber("1100 123456");
        passportDto.setIssuer("MVD RUSSIA №10 in Moscow");
        passportDto.setIssuerNumber("123-456");

        return passportDto;
    }
}