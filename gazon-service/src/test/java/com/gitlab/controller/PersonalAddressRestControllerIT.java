package com.gitlab.controller;

import com.gitlab.dto.PersonalAddressDto;
import com.gitlab.mapper.PersonalAddressMapper;
import com.gitlab.service.PersonalAddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PersonalAddressRestControllerIT extends AbstractIntegrationTest {

    private static final String URN = "/api/personal_address";
    private static final String URI = URL + URN;
    @Autowired
    private PersonalAddressService personalAddressService;
    @Autowired
    private PersonalAddressMapper personalAddressMapper;

    @Test
    void should_get_all_personalAddresses() throws Exception {
        String expected = objectMapper.writeValueAsString(
                personalAddressService
                        .findAll()
                        .stream()
                        .map(personalAddressMapper::toDto)
                        .collect(Collectors.toList())
        );

        mockMvc.perform(get(URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_get_personalAddress_by_id() throws Exception {
        long id = 1L;
        String expected = objectMapper.writeValueAsString(
                personalAddressMapper.toDto(
                        personalAddressService
                                .findById(id)
                                .orElse(null))
        );

        mockMvc.perform(get(URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_return_not_found_when_get_personalAddress_by_non_existent_id() throws Exception {
        long id = 10L;
        mockMvc.perform(get(URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_create_personalAddress() throws Exception {
        PersonalAddressDto personalAddressDto = new PersonalAddressDto();
        personalAddressDto.setAddress("TestAddress");
        personalAddressDto.setDirections("TestDirections");
        personalAddressDto.setApartment("100");
        personalAddressDto.setFloor("13");
        personalAddressDto.setEntrance("5");
        personalAddressDto.setDoorCode("1234");
        personalAddressDto.setPostCode("123456");

        String jsonPersonalAddressDto = objectMapper.writeValueAsString(personalAddressDto);

        mockMvc.perform(post(URI)
                        .content(jsonPersonalAddressDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void should_update_personalAddress_by_id() throws Exception {
        long id = 1L;
        PersonalAddressDto personalAddressDto = new PersonalAddressDto();
        personalAddressDto.setAddress("New Address");
        personalAddressDto.setDirections("New Directions");
        personalAddressDto.setApartment("111");
        personalAddressDto.setFloor("14");
        personalAddressDto.setEntrance("7");
        personalAddressDto.setDoorCode("1244");
        personalAddressDto.setPostCode("123446");

        String jsonPersonalAddressDto = objectMapper.writeValueAsString(personalAddressDto);

        personalAddressDto.setId(id);
        String expected = objectMapper.writeValueAsString(personalAddressDto);

        mockMvc.perform(patch(URI + "/{id}", id)
                        .content(jsonPersonalAddressDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_return_not_found_when_update_personalAddress_by_non_existent_id() throws Exception {
        long id = 10L;
        PersonalAddressDto personalAddressDto = new PersonalAddressDto();
        personalAddressDto.setAddress("New Address");
        personalAddressDto.setDirections("New Directions");
        personalAddressDto.setApartment("111");
        personalAddressDto.setFloor("14");
        personalAddressDto.setEntrance("7");
        personalAddressDto.setDoorCode("1244");
        personalAddressDto.setPostCode("123446");

        String jsonPersonalAddressDto = objectMapper.writeValueAsString(personalAddressDto);

        mockMvc.perform(patch(URI + "/{id}", id)
                        .content(jsonPersonalAddressDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_delete_personalAddress_by_id() throws Exception {
        long id = 2L;
        mockMvc.perform(delete(URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get(URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
