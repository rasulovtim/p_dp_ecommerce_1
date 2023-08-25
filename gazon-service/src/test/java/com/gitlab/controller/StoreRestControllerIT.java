package com.gitlab.controller;

import com.gitlab.dto.StoreDto;
import com.gitlab.mapper.StoreMapper;
import com.gitlab.service.StoreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StoreRestControllerIT extends AbstractIntegrationTest {

    private static final String STORE_URN = "/api/store";
    private static final String STORE_URI = URL + STORE_URN;

    @Autowired
    private StoreService storeService;
    @Autowired
    private StoreMapper storeMapper;

    @Test
    @Transactional
    void should_get_store_by_id() throws Exception {
        long id = 1L;
        String expected = objectMapper.writeValueAsString(
                storeMapper.toDto(
                        storeService
                                .findById(id)
                                .orElse(null))
        );

        mockMvc.perform(get(STORE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_return_not_found_when_get_store_by_non_existent_id() throws Exception {
        long id = -10L;
        mockMvc.perform(get(STORE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_create_store() throws Exception {
        StoreDto storeDto = new StoreDto();
        storeDto.setId(1L);
        storeDto.setOwnerId(1L);

        String jsonProductDto = objectMapper.writeValueAsString(storeDto);

        mockMvc.perform(post(STORE_URI)
                        .content(jsonProductDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void should_return_not_found_when_update_store_by_non_existent_id() throws Exception {
        long id = -10L;
        StoreDto storeDto = new StoreDto();
        storeDto.setId(1L);
        storeDto.setOwnerId(1L);

        String jsonProductDto = objectMapper.writeValueAsString(storeDto);

        mockMvc.perform(patch(STORE_URI + "/{id}", id)
                        .content(jsonProductDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


}
