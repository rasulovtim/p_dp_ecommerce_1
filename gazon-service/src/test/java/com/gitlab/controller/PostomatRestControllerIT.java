package com.gitlab.controller;

import com.gitlab.dto.PostomatDto;
import com.gitlab.mapper.PostomatMapper;
import com.gitlab.service.PostomatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

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

class PostomatRestControllerIT extends AbstractIntegrationTest {

    private static final String URN = "/api/postomat";
    private static final String URI = URL + URN;
    @Autowired
    private PostomatService postomatService;
    @Autowired
    private PostomatMapper postomatMapper;

    @Test
    void should_get_all_postomats() throws Exception {
        String expected = objectMapper.writeValueAsString(
                postomatService
                        .findAll()
                        .stream()
                        .map(postomatMapper::toDto)
                        .collect(Collectors.toList())
        );

        mockMvc.perform(get(URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_get_postomat_by_id() throws Exception {
        long id = 4L;
        String expected = objectMapper.writeValueAsString(
                postomatMapper.toDto(
                        postomatService
                                .findById(id)
                                .orElse(null))
        );

        mockMvc.perform(get(URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_return_not_found_when_get_postomat_by_non_existent_id() throws Exception {
        long id = 20L;
        mockMvc.perform(get(URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_create_postomat() throws Exception {
        PostomatDto postomatDto = generatePostomateDto();

        String jsonPostomatDto = objectMapper.writeValueAsString(postomatDto);

        mockMvc.perform(post(URI)
                        .content(jsonPostomatDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void should_update_postomat_by_id() throws Exception {
        long id = 4L;
        int numberOfEntitiesExpected = postomatService.findAll().size();

        PostomatDto postomatDto = generatePostomateDto();

        String jsonPostomatDto = objectMapper.writeValueAsString(postomatDto);

        postomatDto.setId(id);
        String expected = objectMapper.writeValueAsString(postomatDto);

        mockMvc.perform(patch(URI + "/{id}", id)
                        .content(jsonPostomatDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected))
                .andExpect(result -> assertThat(postomatService.findAll().size(),
                        equalTo(numberOfEntitiesExpected)));
    }

    @Test
    void should_return_not_found_when_update_postomat_by_non_existent_id() throws Exception {
        long id = 20L;
        PostomatDto postomatDto = generatePostomateDto();

        String jsonPostomatDto = objectMapper.writeValueAsString(postomatDto);

        mockMvc.perform(patch(URI + "/{id}", id)
                        .content(jsonPostomatDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_delete_postomat_by_id() throws Exception {
        long id = 5L;
        mockMvc.perform(delete(URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get(URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private PostomatDto generatePostomateDto(){
        PostomatDto postomatDto = new PostomatDto();
        postomatDto.setAddress("TestAddress");
        postomatDto.setDirections("TestDirections");
        postomatDto.setShelfLifeDays((byte) 10);
        return postomatDto;
    }
}