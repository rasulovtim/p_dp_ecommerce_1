package com.gitlab.mapper;

import com.gitlab.controller.AbstractIntegrationTest;
import com.gitlab.dto.*;
import com.gitlab.enums.Gender;
import com.gitlab.model.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ShoppingCartMapperTest extends AbstractIntegrationTest {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    private static final Logger log = LoggerFactory.getLogger(ShoppingCartMapperTest.class);


    @Test
    void should_map_ShoppingCart_to_Dto() {
        log.info("Running should_map_ShoppingCart_to_Dto test");

        // Создаем тестовые объекты
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);

        // Создаем тестового пользователя с помощью метода generateUser()
        UserDto userDto = generateUser();

        // Преобразуем UserDto в User
        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());

        shoppingCart.setUser(user);

        // Создаем тестовый объект SelectedProduct
        SelectedProduct selectedProduct = new SelectedProduct();
        selectedProduct.setProduct(new Product()); // Создайте объект Product или установите его данные

        Set<SelectedProduct> selectedProducts = new HashSet<>();
        selectedProducts.add(selectedProduct);

        shoppingCart.setSelectedProducts(selectedProducts);

        // Маппинг
        log.debug("Mapping ShoppingCart to ShoppingCartDto...");
        ShoppingCartDto dtoTwin = shoppingCartMapper.toDto(shoppingCart);
        log.debug("Mapping completed");

        // Проверки
        assertNotNull(dtoTwin);
        log.debug("Comparing ids...");
        assertEquals(shoppingCart.getId(), dtoTwin.getId());
        log.debug("Comparing selected products sizes...");
        assertEquals(shoppingCart.getSelectedProducts().size(), dtoTwin.getSelectedProducts().size());

        log.info("should_map_ShoppingCart_to_Dto test completed");
    }

    @Test
    void should_map_ShoppingCartDto_to_Entity() {
        log.info("Running should_map_ShoppingCartDto_to_Entity test");

        // Создаем тестовые объекты
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setUserId(1L);
        shoppingCartDto.setSum(BigDecimal.valueOf(100));
        shoppingCartDto.setTotalWeight(500L);

        SelectedProductDto selectedProductDto = new SelectedProductDto();
        selectedProductDto.setProductId(1L);
        selectedProductDto.setCount(1);

        Set<SelectedProductDto> selectedProductDtos = new HashSet<>();
        selectedProductDtos.add(selectedProductDto);

        shoppingCartDto.setSelectedProducts(selectedProductDtos);

        // Маппинг
        log.debug("Mapping ShoppingCartDto to ShoppingCart...");
        ShoppingCart entityTwin = shoppingCartMapper.toEntity(shoppingCartDto);
        log.debug("Mapping completed");

        // Проверки
        assertNotNull(entityTwin);
        log.debug("Comparing selected products sizes...");
        assertEquals(shoppingCartDto.getSelectedProducts().size(), entityTwin.getSelectedProducts().size());

        log.info("should_map_ShoppingCartDto_to_Entity test completed");
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
                Gender.MALE,
                "89007777777",
                passportDto,
                personalAddress,
                bankCardSet,
                roleSet
        );
    }

}
