package com.gitlab.controller;

import com.gitlab.dto.PickupPointDto;
import com.gitlab.enums.PickupPointFeatures;
import com.gitlab.mapper.PickupPointMapper;
import com.gitlab.service.PickupPointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

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

class PickupPointRestControllerIT extends AbstractIntegrationTest {

    private static final String URN = "/api/pickup-point";
    private static final String URI = URL + URN;
    @Autowired
    private PickupPointService pickupPointService;
    @Autowired
    private PickupPointMapper pickupPointMapper;

    @Test
    @Transactional(readOnly = true)
    void should_get_all_pickupPoints() throws Exception {

        var response = pickupPointService.getPage(null, null);
        var expected = objectMapper.writeValueAsString(pickupPointMapper.toDtoList(response.getContent()));

        mockMvc.perform(get(URI))
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

        var response = pickupPointService.getPage(page, size);
        assertFalse(response.getContent().isEmpty());

        var expected = objectMapper.writeValueAsString(pickupPointMapper.toDtoList(response.getContent()));

        mockMvc.perform(get(URI + parameters))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_get_page_with_incorrect_parameters() throws Exception {
        int page = 0;
        int size = -2;
        String parameters = "?page=" + page + "&size=" + size;

        mockMvc.perform(get(URI + parameters))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void should_get_page_without_content() throws Exception {
        int page = 10;
        int size = 100;
        String parameters = "?page=" + page + "&size=" + size;

        mockMvc.perform(get(URI + parameters))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    void should_get_pickupPoint_by_id() throws Exception {
        long id = 7L;
        String expected = objectMapper.writeValueAsString(
                pickupPointMapper.toDto(
                        pickupPointService
                                .findById(id)
                                .orElse(null))
        );

        mockMvc.perform(get(URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_return_not_found_when_get_pickupPoint_by_non_existent_id() throws Exception {
        long id = 10L;
        mockMvc.perform(get(URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_create_pickupPoint() throws Exception {
        PickupPointDto pickupPointDto = generatePickupPointDto();

        String jsonPickupPointDto = objectMapper.writeValueAsString(pickupPointDto);

        mockMvc.perform(post(URI)
                        .content(jsonPickupPointDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void should_update_pickupPoint_by_id() throws Exception {
        long id = 7L;
        int numberOfEntitiesExpected = pickupPointService.findAll().size();

        PickupPointDto pickupPointDto = generatePickupPointDto();

        String jsonPickupPointDto = objectMapper.writeValueAsString(pickupPointDto);

        pickupPointDto.setId(id);
        String expected = objectMapper.writeValueAsString(pickupPointDto);

        mockMvc.perform(patch(URI + "/{id}", id)
                        .content(jsonPickupPointDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected))
                .andExpect(result -> assertThat(pickupPointService.findAll().size(),
                        equalTo(numberOfEntitiesExpected)));
    }

    @Test
    void should_return_not_found_when_update_pickupPoint_by_non_existent_id() throws Exception {
        long id = 10L;
        PickupPointDto pickupPointDto = generatePickupPointDto();

        String jsonPickupPointDto = objectMapper.writeValueAsString(pickupPointDto);

        mockMvc.perform(patch(URI + "/{id}", id)
                        .content(jsonPickupPointDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_delete_pickupPoint_by_id() throws Exception {
        PickupPointDto pickupPointDto = pickupPointService.saveDto(generatePickupPointDto());
        long id = pickupPointDto.getId();
        mockMvc.perform(delete(URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get(URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private PickupPointDto generatePickupPointDto() {
        PickupPointDto pickupPointDto = new PickupPointDto();

        pickupPointDto.setAddress("TestAddress");
        pickupPointDto.setDirections("TestDirections");
        pickupPointDto.setShelfLifeDays((byte) 16);
        pickupPointDto.setPickupPointFeatures(Set.of(PickupPointFeatures.values()));

        return pickupPointDto;
    }
}