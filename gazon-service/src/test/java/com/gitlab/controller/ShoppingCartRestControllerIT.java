package com.gitlab.controller;

import com.gitlab.dto.ShoppingCartDto;
import com.gitlab.mapper.ShoppingCartMapper;
import com.gitlab.service.ShoppingCartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.org.hamcrest.CoreMatchers.equalTo;
import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;

class ShoppingCartRestControllerIT extends AbstractIntegrationTest {

    private static final String SHOPPING_CART_URN = "/api/shopping_cart";
    private static final String SHOPPING_CART_URI = URL + SHOPPING_CART_URN;

    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Transactional
    @Test
    void should_return_not_found_when_get_shoppingCart_by_non_existent_id() throws Exception {
        long id = 10L;
        mockMvc.perform(get(SHOPPING_CART_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_create_shoppingCart() throws Exception {
        ShoppingCartDto shoppingCartDto = generateShoppingCartDto();
        String jsonShoppingCartDto = objectMapper.writeValueAsString(shoppingCartDto);

        mockMvc.perform(post(SHOPPING_CART_URI)
                        .content(jsonShoppingCartDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Transactional
    @Test
    void should_update_shoppingCart_by_id() throws Exception {
        long id = 1L;
        int numberOfEntitiesExpected = shoppingCartService.findAll().size();
        ShoppingCartDto shoppingCartDto = generateShoppingCartDto(2L);
        String jsonShoppingCartDto = objectMapper.writeValueAsString(shoppingCartDto);

        shoppingCartDto.setId(id);
        String expected = objectMapper.writeValueAsString(shoppingCartDto);

        mockMvc.perform(patch(SHOPPING_CART_URI + "/{id}", id)
                        .content(jsonShoppingCartDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected))
                .andExpect(result -> assertThat(shoppingCartService.findAll().size(),
                        equalTo(numberOfEntitiesExpected)));
    }

    @Transactional
    @Test
    void should_return_not_found_when_update_shoppingCart_by_non_existent_id() throws Exception {
        long id = 10L;
        ShoppingCartDto shoppingCartDto = generateShoppingCartDto();
        String jsonShoppingCartDto = objectMapper.writeValueAsString(shoppingCartDto);

        mockMvc.perform(patch(SHOPPING_CART_URI + "/{id}", id)
                        .content(jsonShoppingCartDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    void should_get_all_shoppingCarts() throws Exception {
        List<ShoppingCartDto> shoppingCarts = shoppingCartService.findAll().stream().map(shoppingCartMapper::toDto).toList();
        String expected = objectMapper.writeValueAsString(shoppingCarts);

        mockMvc.perform(get(SHOPPING_CART_URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_get_shoppingCart_by_id() throws Exception {
        long id = 1L;
        var shoppingCart = shoppingCartService.findById(id).orElse(null);
        var shoppingCartDto = shoppingCartMapper.toDto(shoppingCart);
        String expected = objectMapper.writeValueAsString(shoppingCartDto);

        mockMvc.perform(get(SHOPPING_CART_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Transactional
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

    private ShoppingCartDto generateShoppingCartDto(Long id){
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setUserId(id);

        return shoppingCartDto;
    }

    private ShoppingCartDto generateShoppingCartDto(){
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setUserId(1L);
        shoppingCartDto.setSum(BigDecimal.valueOf(100));
        shoppingCartDto.setTotalWeight(500L);

        return  shoppingCartDto;
    }
}