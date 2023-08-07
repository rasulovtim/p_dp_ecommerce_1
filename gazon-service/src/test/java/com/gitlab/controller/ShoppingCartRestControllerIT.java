package com.gitlab.controller;

import com.gitlab.dto.ShoppingCartDto;
import com.gitlab.mapper.ShoppingCartMapper;
import com.gitlab.model.ShoppingCart;
import com.gitlab.service.ShoppingCartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ShoppingCartRestControllerIT extends AbstractIntegrationTest {

    private static final String SHOPPING_CART_URN = "/api/shopping_cart";
    private static final String SHOPPING_CART_URI = URL + SHOPPING_CART_URN;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Test
    void should_get_shoppingCart_by_id() throws Exception {
        long id = 1L;
        var shoppingCart = shoppingCartService.getShoppingCartById(id).orElse(null);
        var shoppingCartDto = shoppingCartMapper.toDto(shoppingCart);
        String expected = objectMapper.writeValueAsString(shoppingCartDto);

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
        ShoppingCartDto shoppingCartDto = generateShoppingCartDto();

        // Установите корректный идентификатор пользователя
        shoppingCartDto.setUserId(1L);

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
        ShoppingCart shoppingCart = generateShoppingCart();
        shoppingCart.setId(id);
        shoppingCartService.createShoppingCart(shoppingCart);

        ShoppingCartDto shoppingCartDto = generateShoppingCartDto();
        shoppingCartDto.setId(id);

        String jsonShoppingCartDto = objectMapper.writeValueAsString(shoppingCartDto);
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
    void should_delete_shoppingCart_by_id() throws Exception {
        long id = 3L;
        mockMvc.perform(delete(SHOPPING_CART_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get(SHOPPING_CART_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private ShoppingCartDto generateShoppingCartDto() {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setUserId(1L);  // Установите корректное значение userId
        shoppingCartDto.setSelectedProducts(Collections.singleton("product1"));
        shoppingCartDto.setSum(BigDecimal.valueOf(100));
        shoppingCartDto.setTotalWeight(500L);

        return shoppingCartDto;
    }

    private ShoppingCart generateShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setSelectedProducts(Collections.singleton("product1"));
        shoppingCart.setSum(BigDecimal.valueOf(100));
        shoppingCart.setTotalWeight(500L);
        return shoppingCart;
    }
}
