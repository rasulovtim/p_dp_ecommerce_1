package com.gitlab.controller;

import com.gitlab.dto.*;
import com.gitlab.mapper.ShoppingCartMapper;
import com.gitlab.mapper.UserMapper;
import com.gitlab.model.Passport;
import com.gitlab.model.ShoppingCart;
import com.gitlab.model.User;
import com.gitlab.service.ShoppingCartService;
import com.gitlab.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ShoppingCartRestControllerIT extends AbstractIntegrationTest {

    private static final String SHOPPING_CART_URN = "/api/shopping-carts";
    private static final String SHOPPING_CART_URI = URL + SHOPPING_CART_URN;

    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    private UserDto userDto;
    private ShoppingCartDto shoppingCartDto;
    private User user;

    @BeforeEach
    void setUp() {

        // Create a user for the test
        userDto = generateUser();
        user = userMapper.toEntity(userDto);
        userService.save(user);

        // Create a DTO for ShoppingCart
        shoppingCartDto = generateShoppingCartDto();
    }

    @Test
    void should_create_shoppingCart() throws Exception {

        String jsonShoppingCartDto = objectMapper.writeValueAsString(shoppingCartDto);

        mockMvc.perform(post(SHOPPING_CART_URI)
                        .content(jsonShoppingCartDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }


    @Test
    void should_return_not_found_when_get_shopping_cart_by_non_existent_id() throws Exception {
        long id = 10L;
        mockMvc.perform(get(SHOPPING_CART_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    void should_delete_shopping_cart_by_id() throws Exception {
        long id = 2L;
        mockMvc.perform(delete(SHOPPING_CART_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get(SHOPPING_CART_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_all_shopping_carts() throws Exception {
        // Generate a list of sample shopping carts
        List<ShoppingCart> shoppingCarts = generateShoppingCarts();

        // Mock the behavior of the shoppingCartService to return the list of shopping carts
        when(shoppingCartService.getAllShoppingCarts()).thenReturn(shoppingCarts);

        mockMvc.perform(get(SHOPPING_CART_URI)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(shoppingCarts.size())))
                .andExpect(jsonPath("$[0].userId", is(shoppingCarts.get(0).getUser().getId().intValue())))
                .andExpect(jsonPath("$[1].userId", is(shoppingCarts.get(1).getUser().getId().intValue())));
    }

    @Test
    void should_update_shopping_cart_by_id() throws Exception {
        long id = 1L;


        String jsonShoppingCartDto = objectMapper.writeValueAsString(shoppingCartDto);

        MvcResult result = mockMvc.perform(patch("/api/shopping-carts/{id}", id)
                        .content(jsonShoppingCartDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn(); // Получаем результат выполнения запроса

        String responseContent = result.getResponse().getContentAsString();
        ShoppingCartDto responseDto = objectMapper.readValue(responseContent, ShoppingCartDto.class);

        // Сравните ожидаемый DTO с возвращаемым DTO
        assertThat(responseDto)
                .usingRecursiveComparison()
                .isEqualTo(shoppingCartDto); // Используйте исходный shoppingCartDto
    }


    private UserDto generateUser() {
        Set<String> roleSet = new HashSet<>();
        roleSet.add("ROLE_ADMIN");

        Set<BankCardDto> bankCardSet = new HashSet<>();
        bankCardSet.add(new BankCardDto(
                1L,
                "1111222233334444",
                LocalDate.now(),
                423
        ));

        Set<ShippingAddressDto> personalAddress = new HashSet<>();
        personalAddress.add(new PersonalAddressDto(
                1L,
                "address",
                "directions",
                "apartment",
                "floor",
                "entrance",
                "doorCode",
                "postCode"));

        PassportDto passportDto = new PassportDto(
                1L,
                Passport.Citizenship.RUSSIA,
                "user",
                "user",
                "patronym",
                LocalDate.now(),
                LocalDate.now(),
                "098765",
                "issuer",
                "issuerN");


        return new UserDto(
                1L,
                "mail@mail.ru",
                "user",
                "answer",
                "question",
                "user",
                "user",
                LocalDate.now(),
                User.Gender.MALE,
                "89007777777",
                passportDto,
                personalAddress,
                bankCardSet,
                roleSet
        );
    }

    private ShoppingCartDto generateShoppingCartDto() {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setUserId(user.getId());
        shoppingCartDto.setSelectedProducts(Set.of("product1", "product2"));
        shoppingCartDto.setSum(BigDecimal.valueOf(100));
        shoppingCartDto.setTotalWeight(500L);
        return shoppingCartDto;
    }

    private ShoppingCart generateShoppingCartFromDto(ShoppingCartDto shoppingCartDto) {
        ShoppingCart shoppingCart = shoppingCartMapper.toEntity(shoppingCartDto);
        shoppingCart.setUser(userMapper.toEntity(generateUser()));
        return shoppingCart;
    }

    private List<ShoppingCart> generateShoppingCarts() {
        List<ShoppingCart> shoppingCarts = new ArrayList<>();

        // Create multiple sample shopping carts and add them to the list
        ShoppingCart shoppingCart1 = generateShoppingCart(1L);
        ShoppingCart shoppingCart2 = generateShoppingCart(2L);
        shoppingCarts.add(shoppingCart1);
        shoppingCarts.add(shoppingCart2);

        return shoppingCarts;
    }

    private ShoppingCart generateShoppingCart(Long id) {
        UserDto userDto = generateUser(); // Generate a sample user DTO
        User user = userMapper.toEntity(userDto); // Convert user DTO to user entity

        Set<String> selectedProducts = Set.of("product1", "product2");

        return new ShoppingCart(id, user, selectedProducts);
    }
}
