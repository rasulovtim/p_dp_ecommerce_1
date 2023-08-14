package com.gitlab.mapper;

import com.gitlab.dto.ShoppingCartDto;
import com.gitlab.model.ShoppingCart;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartMapperTest {

    private final ShoppingCartMapper mapper = Mappers.getMapper(ShoppingCartMapper.class);

    @Test
    void should_map_shoppingCart_to_Dto() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        // Set other properties

        ShoppingCartDto actualResult = mapper.toDto(shoppingCart);

        assertNotNull(actualResult);
        assertEquals(shoppingCart.getId(), actualResult.getId());
        // Assert other properties
    }

    @Test
    void should_map_shoppingCartDto_to_Entity() {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setId(1L);
        // Set other properties

        ShoppingCart actualResult = mapper.toEntity(shoppingCartDto);

        assertNotNull(actualResult);
        assertEquals(shoppingCartDto.getId(), actualResult.getId());
        // Assert other properties
    }
}
