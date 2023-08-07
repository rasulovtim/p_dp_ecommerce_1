package com.gitlab.controller;

import com.gitlab.dto.ShoppingCartDto;
import com.gitlab.mapper.ShoppingCartMapper;
import com.gitlab.model.ShoppingCart;
import com.gitlab.model.User;
import com.gitlab.repository.UserRepository;
import com.gitlab.service.ShoppingCartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ShoppingCartRestControllerIT extends AbstractIntegrationTest {
    @Autowired
    private UserRepository userRepository;
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
        // Создаем и сохраняем пользователя в базе данных
        User user = generateUser();
        userRepository.save(user);

        ShoppingCartDto shoppingCartDto = generateShoppingCartDto();
        shoppingCartDto.setUserId(user.getId()); // Устанавливаем ID пользователя

        // Создаем объект ShoppingCart с заполненными полями
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCart.setSelectedProducts(shoppingCartDto.getSelectedProducts());
        shoppingCart.setSum(shoppingCartDto.getSum());
        shoppingCart.setTotalWeight(shoppingCartDto.getTotalWeight());

        // Преобразуем объект ShoppingCart в JSON
        String jsonShoppingCart = objectMapper.writeValueAsString(shoppingCart);

        mockMvc.perform(post(SHOPPING_CART_URI)
                        .content(jsonShoppingCart)
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
        shoppingCartDto.setId(1L); // Устанавливаем id
        shoppingCartDto.setUserId(1L);
        shoppingCartDto.setSelectedProducts(Collections.singleton("product1"));
        shoppingCartDto.setSum(BigDecimal.valueOf(100));
        shoppingCartDto.setTotalWeight(500L);
        return shoppingCartDto;
    }


    private ShoppingCart generateShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setSelectedProducts(Collections.singleton("product1"));
        shoppingCart.setSum(BigDecimal.valueOf(100));
        shoppingCart.setTotalWeight(500L);
        return shoppingCart;
    }
    private User generateUser() {
        User user = new User();
        user.setId(1L); // Временное значение для тестирования
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setSecurityQuestion("Security question");
        user.setAnswerQuestion("Answer question");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setGender(User.Gender.MALE);
        user.setPhoneNumber("1234567890");

        // Установите остальные поля пользователя
        user.setCreateDate(LocalDate.now());

        return user;
    }


}
