package com.gitlab.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitlab.config.PostgresSqlContainer;
import com.gitlab.dto.SelectedProductDto;
import com.gitlab.dto.ShoppingCartDto;
import com.gitlab.mapper.SelectedProductMapper;
import com.gitlab.model.SelectedProduct;
import com.gitlab.service.SelectedProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ShoppingCartRestControllerIT extends AbstractIntegrationTest {

    private static final String SHOPPING_CART_URN = "/api/shopping_cart";
    private static final String SHOPPING_CART_URI = URL + SHOPPING_CART_URN;

    @Autowired
    private SelectedProductService selectedProductService;
    @Autowired
    private SelectedProductMapper selectedProductMapper;

    @Test
    void should_get_all_shoppingCarts() throws Exception {
        String expected = objectMapper.writeValueAsString(
                selectedProductService
                        .findAll()
                        .stream()
                        .map(selectedProductMapper::toDto)
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
        var selectedProduct = selectedProductService.findById(id).orElse(null);
        var selectedProductDto = selectedProductMapper.toDto(selectedProduct);
        selectedProductMapper.calculatedUnmappedFields(selectedProductDto, selectedProduct);
        String expected = objectMapper.writeValueAsString(selectedProductDto);

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
        // Создайте и сохраните SelectedProduct
        SelectedProductDto selectedProductDto = generateSelectedProductDto();
        SelectedProduct selectedProduct = selectedProductMapper.toEntity(selectedProductDto);
        selectedProduct = selectedProductService.save(selectedProduct);

        // Создайте ShoppingCart и добавьте в него сохраненный SelectedProduct
        ShoppingCartDto shoppingCartDto = generateShoppingCartDto();
        shoppingCartDto.setUserId(1L); // Установите правильное значение user_id


        // Создайте и добавьте Set с идентификаторами выбранных продуктов в ShoppingCartDto
        Set<String> selectedProductIds = new HashSet<>();
        selectedProductIds.add(String.valueOf(selectedProduct.getId())); // Используйте id сохраненного SelectedProduct
        shoppingCartDto.setSelectedProducts(selectedProductIds);

        shoppingCartDto.setSum(selectedProductDto.getSum()); // Устанавливаем сумму
        shoppingCartDto.setTotalWeight(selectedProductDto.getTotalWeight()); // Устанавливаем общий вес
        String jsonShoppingCartDto = objectMapper.writeValueAsString(shoppingCartDto);

        mockMvc.perform(post(SHOPPING_CART_URI)
                        .content(jsonShoppingCartDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }






    private Set<SelectedProductDto> generateSelectedProductSet() {
        Set<SelectedProductDto> selectedProducts = new HashSet<>();

        SelectedProductDto selectedProduct1 = new SelectedProductDto();
        selectedProduct1.setProductId(1L);
        selectedProduct1.setCount(3);
        selectedProduct1.setSum(BigDecimal.valueOf(100));
        selectedProduct1.setTotalWeight(500L);

        // Добавьте другие выбранные продукты, если необходимо

        selectedProducts.add(selectedProduct1);
        return selectedProducts;
    }


    private ShoppingCartDto generateShoppingCartDto() {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setUserId(1L);

        // Здесь вы также можете установить другие необходимые поля ShoppingCartDto

        return shoppingCartDto;
    }


    @Test
    void should_update_shoppingCart_by_id() throws Exception {
        long id = 1L;
        SelectedProductDto selectedProductDto = generateSelectedProductDto();

        String jsonSelectedProductDto = objectMapper.writeValueAsString(selectedProductDto);

        selectedProductDto.setId(id);
        String expected = objectMapper.writeValueAsString(selectedProductDto);

        mockMvc.perform(patch(SHOPPING_CART_URI + "/{id}", id)
                        .content(jsonSelectedProductDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_return_not_found_when_update_shoppingCart_by_non_existent_id() throws Exception {
        long id = 10L;
        SelectedProductDto selectedProductDto = generateSelectedProductDto();

        String jsonSelectedProductDto = objectMapper.writeValueAsString(selectedProductDto);

        mockMvc.perform(patch(SHOPPING_CART_URI + "/{id}", id)
                        .content(jsonSelectedProductDto)
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

    private SelectedProductDto generateSelectedProductDto() {
        SelectedProductDto selectedProduct = new SelectedProductDto();
        selectedProduct.setProductId(1L);
        selectedProduct.setCount(3);
        selectedProduct.setSum(BigDecimal.valueOf(100));  // Set the appropriate sum value
        selectedProduct.setTotalWeight(500L);  // Set the appropriate total weight value

        return selectedProduct;
    }
}