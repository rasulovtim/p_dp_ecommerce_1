package com.gitlab.mapper;

import com.gitlab.dto.ShoppingCartDto;
import com.gitlab.model.ShoppingCart;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ShoppingCartMapperTest {

    private final ShoppingCartMapper mapper = Mappers.getMapper(ShoppingCartMapper.class);

    @Test
    void should_map_shoppingCart_to_Dto() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setTotalWeight(100L);
        shoppingCart.setSum(BigDecimal.valueOf(100.50));

        Set<String> selectedProducts = new HashSet<>();
        selectedProducts.add("Product1");
        selectedProducts.add("Product2");
        shoppingCart.setSelectedProducts(selectedProducts);

        ShoppingCartDto actualResult = mapper.toDto(shoppingCart);

        assertNotNull(actualResult);
        assertEquals(shoppingCart.getId(), actualResult.getId());
        assertEquals(shoppingCart.getTotalWeight(), actualResult.getTotalWeight());
        assertEquals(shoppingCart.getSum(), actualResult.getSum());
        assertEquals(shoppingCart.getSelectedProducts(), actualResult.getSelectedProducts());
    }

    @Test
    void should_map_shoppingCartDto_to_Entity() {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setId(1L);
        shoppingCartDto.setTotalWeight(100L);
        shoppingCartDto.setSum(BigDecimal.valueOf(100.50));

        Set<String> selectedProducts = new HashSet<>();
        selectedProducts.add("Product1");
        selectedProducts.add("Product2");
        shoppingCartDto.setSelectedProducts(selectedProducts);

        ShoppingCart actualResult = mapper.toEntity(shoppingCartDto);

        assertNotNull(actualResult);
        assertEquals(shoppingCartDto.getId(), actualResult.getId());
        assertEquals(shoppingCartDto.getTotalWeight(), actualResult.getTotalWeight());
        assertEquals(shoppingCartDto.getSum(), actualResult.getSum());
        assertEquals(shoppingCartDto.getSelectedProducts(), actualResult.getSelectedProducts());
    }
}
