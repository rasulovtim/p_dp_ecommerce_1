package com.gitlab.controller;

import com.gitlab.dto.PickupPointDto;
import com.gitlab.enums.PickupPointFeatures;
import com.gitlab.mapper.PickupPointMapper;
import com.gitlab.service.PickupPointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

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

class PickupPointRestControllerIT extends AbstractIntegrationTest {

    private static final String URN = "/api/pickup_point";
    private static final String URI = URL + URN;
    @Autowired
    private PickupPointService pickupPointService;
    @Autowired
    private PickupPointMapper pickupPointMapper;

    @Test
    void should_get_all_pickupPoints() throws Exception {
        String expected = objectMapper.writeValueAsString(
                pickupPointService
                        .findAll()
                        .stream()
                        .map(pickupPointMapper::toDto)
                        .collect(Collectors.toList())
        );

        mockMvc.perform(get(URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
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