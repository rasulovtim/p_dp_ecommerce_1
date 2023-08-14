package com.gitlab.controller;

import com.gitlab.dto.*;
import com.gitlab.mapper.*;
import com.gitlab.model.Passport;
import com.gitlab.model.Product;
import com.gitlab.model.SelectedProduct;
import com.gitlab.model.User;
import com.gitlab.service.ProductService;
import com.gitlab.service.SelectedProductService;
import com.gitlab.service.ShoppingCartService;
import com.gitlab.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ShoppingCartRestControllerIT extends AbstractIntegrationTest {

    private static final String SHOPPING_CART_URN = "/api/shopping_cart";
    private static final String SHOPPING_CART_URI = URL + SHOPPING_CART_URN;

    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    SelectedProductService selectedProductService;
    @Autowired
    SelectedProductMapper selectedProductMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProductService productService;

    private UserDto userDto;

    private ShoppingCartDto shoppingCartDto;

    private User user;

    private ProductDto productDto;
    private SelectedProductDto selectedProductDto;

    @BeforeEach
    void setUp() {
        System.out.println("Setting up test...");

        // Создание пользователя для теста
        userDto = generateUser();
        user = userMapper.toEntity(userDto);
        user = userService.save(user); // Вернуть сохраненного пользователя
        System.out.println("User created with ID: " + user.getId());

        // Создание продукта для теста
        productDto = generateProductDto();
        Product product = productService.save(productMapper.toEntity(productDto));
        System.out.println("Product created with ID: " + product.getId());

        // Создание выбранного продукта для теста
        selectedProductDto = generateSelectedProductDto(product.getId());
        SelectedProduct selectedProduct = selectedProductMapper.toEntity(selectedProductDto);
        selectedProduct = selectedProductService.save(selectedProduct);
        System.out.println("SelectedProduct created with ID: " + selectedProduct.getId());

        // Создание корзины для теста
        shoppingCartDto = generateShoppingCartDto(user.getId(), selectedProductDto);
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
    void should_get_shoppingCart_by_id() throws Exception {
        String jsonShoppingCartDto = objectMapper.writeValueAsString(shoppingCartDto);

        mockMvc.perform(post(SHOPPING_CART_URI)
                .content(jsonShoppingCartDto)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        long id = 1L;

        mockMvc.perform(get(SHOPPING_CART_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private ShoppingCartDto generateShoppingCartDto(Long userId, SelectedProductDto selectedProductDto) {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setUserId(userId);
        shoppingCartDto.setSum(BigDecimal.valueOf(100));
        shoppingCartDto.setTotalWeight(500L);

        Set<SelectedProductDto> selectedProducts = new HashSet<>();
        selectedProducts.add(selectedProductDto);
        shoppingCartDto.setSelectedProducts(selectedProducts);

        return shoppingCartDto;
    }

    private SelectedProductDto generateSelectedProductDto(Long productId) {
        SelectedProductDto selectedProductDto = new SelectedProductDto();
        selectedProductDto.setProductId(productId);
        selectedProductDto.setCount(3);
        selectedProductDto.setSum(BigDecimal.valueOf(300));
        selectedProductDto.setTotalWeight(150L);
        return selectedProductDto;
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

    private ProductDto generateProductDto() {
        ProductDto productDto = new ProductDto();
        productDto.setName("name1");
        productDto.setStockCount(1);
        productDto.setImagesId(new Long[]{1L});
        productDto.setDescription("name");
        productDto.setIsAdult(true);
        productDto.setCode("name");
        productDto.setWeight(1L);
        productDto.setPrice(BigDecimal.ONE);
        return productDto;
    }
}
