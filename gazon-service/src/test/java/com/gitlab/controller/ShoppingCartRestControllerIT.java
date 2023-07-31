package com.gitlab.controller;


import com.gitlab.dto.ShoppingCartDto;
import com.gitlab.mapper.ShoppingCartMapper;

import com.gitlab.service.ShoppingCartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ShoppingCartRestControllerIT extends AbstractIntegrationTest {

    private static final String SHOPPING_CART_URN = "/api/shopping-cart";
    private static final String SHOPPING_CART_URI = URL + SHOPPING_CART_URN;

    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Test
    void should_get_all_shoppingCarts() throws Exception {
        String expected = objectMapper.writeValueAsString(
                shoppingCartService
                        .findAll()
                        .stream()
                        .map(shoppingCartMapper::toDto)
                        .collect(Collectors.toList())
        );

        mockMvc.perform(get(SHOPPING_CART_URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_get_shoppingCart_by_id() throws Exception {
        long id = 1L;
        String expected = objectMapper.writeValueAsString(
                shoppingCartMapper.toDto(
                        shoppingCartService
                                .findById(id)
                                .orElse(null))
        );

        mockMvc.perform(get(SHOPPING_CART_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_return_not_found_when_get_shoppingCart_by_non_existent_id() throws Exception {
        long id = 10L;
        mockMvc.perform(get(SHOPPING_CART_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_create_shoppingCart() throws Exception {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();


        String jsonShoppingCartDto = objectMapper.writeValueAsString(shoppingCartDto);

        mockMvc.perform(post(SHOPPING_CART_URI)
                        .content(jsonShoppingCartDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void should_update_shoppingCart_by_id() throws Exception {
        long id = 1L;
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();


        String jsonShoppingCartDto = objectMapper.writeValueAsString(shoppingCartDto);

        shoppingCartDto.setId(id);
        String expected = objectMapper.writeValueAsString(shoppingCartDto);

        mockMvc.perform(patch(SHOPPING_CART_URI + "/{id}", id)
                        .content(jsonShoppingCartDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_return_not_found_when_update_shoppingCart_by_non_existent_id() throws Exception {
        long id = 10L;
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();


        String jsonShoppingCartDto = objectMapper.writeValueAsString(shoppingCartDto);

        mockMvc.perform(patch(SHOPPING_CART_URI + "/{id}", id)
                        .content(jsonShoppingCartDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_delete_shoppingCart_by_id() throws Exception {
        long id = 2L;
        mockMvc.perform(delete(SHOPPING_CART_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get(SHOPPING_CART_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
