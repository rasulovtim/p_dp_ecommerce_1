package com.gitlab.mapper;

import com.gitlab.dto.ShoppingCartDto;
import com.gitlab.model.ShoppingCart;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartMapperTest {

    private final ShoppingCartMapper mapper = Mappers.getMapper(ShoppingCartMapper.class);

    @Test
    void should_map_shoppingCart_to_Dto() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);

        Set<String> selectedProducts = new HashSet<>();
        selectedProducts.add("Product1");
        selectedProducts.add("Product2");
        shoppingCart.setSelectedProducts(selectedProducts);

        ShoppingCartDto actualResult = mapper.toDto(shoppingCart);

        assertNotNull(actualResult);
        assertEquals(shoppingCart.getId(), actualResult.getId());
        assertEquals(shoppingCart.getSelectedProducts(), actualResult.getSelectedProducts());
    }

    @Test
    void should_map_shoppingCartDto_to_Entity() {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setId(1L);

        Set<String> selectedProducts = new HashSet<>();
        selectedProducts.add("Product1");
        selectedProducts.add("Product2");
        shoppingCartDto.setSelectedProducts(selectedProducts);

        ShoppingCart actualResult = mapper.toEntity(shoppingCartDto);

        assertNotNull(actualResult);
        assertEquals(shoppingCartDto.getId(), actualResult.getId());
        assertEquals(shoppingCartDto.getSelectedProducts(), actualResult.getSelectedProducts());
    }

    @Test
    void should_map_null_shoppingCart_to_Dto() {
        ShoppingCartDto dto = mapper.toDto(null);
        assertNull(dto);
    }

    @Test
    void should_map_null_shoppingCartDto_to_Entity() {
        ShoppingCart entity = mapper.toEntity(null);
        assertNull(entity);
    }

    @Test
    void should_map_empty_selectedProducts_to_Dto() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setSelectedProducts(Collections.emptySet());

        ShoppingCartDto dto = mapper.toDto(shoppingCart);

        assertNotNull(dto);
        assertTrue(dto.getSelectedProducts().isEmpty());
    }

    @Test
    void should_map_empty_selectedProducts_to_Entity() {
        ShoppingCartDto dto = new ShoppingCartDto();
        dto.setSelectedProducts(Collections.emptySet());

        ShoppingCart entity = mapper.toEntity(dto);

        assertNotNull(entity);
        assertTrue(entity.getSelectedProducts().isEmpty());
    }


}
