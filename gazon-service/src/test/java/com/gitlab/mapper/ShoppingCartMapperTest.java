package com.gitlab.mapper;

import com.gitlab.controller.AbstractIntegrationTest;
import com.gitlab.dto.SelectedProductDto;
import com.gitlab.dto.ShoppingCartDto;
import com.gitlab.model.SelectedProduct;
import com.gitlab.model.ShoppingCart;
import com.gitlab.model.User;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ShoppingCartMapperTest extends AbstractIntegrationTest {

    @Autowired
    private ShoppingCartMapper mapper;

    @Test
    void should_map_productImage_to_Dto() {
        ShoppingCart shoppingCart = getShoppingCart(1L);

        ShoppingCartDto dtoTwin = mapper.toDto(shoppingCart);

        assertNotNull(dtoTwin);
        assertEquals(shoppingCart.getId(), dtoTwin.getId());
        assertEquals(shoppingCart.getUser().getId(), dtoTwin.getUserId());
        assertEquals(shoppingCart.getSelectedProducts().size(), dtoTwin.getSelectedProducts().size());
    }

    @Test
    void should_map_productImageDto_to_Entity() {
        ShoppingCartDto shoppingCartDto = getShoppingCartDto(1L);

        ShoppingCart entityTwin = mapper.toEntity(shoppingCartDto);

        assertNotNull(entityTwin);
        assertEquals(shoppingCartDto.getId(), entityTwin.getId());
        assertEquals(shoppingCartDto.getUserId(), entityTwin.getUser().getId());
        assertEquals(shoppingCartDto.getSelectedProducts().size(), entityTwin.getSelectedProducts().size());
    }


    @Test
    void should_map_shoppingCartList_to_DtoList() {
        List<ShoppingCart> shoppingCartList = List.of(getShoppingCart(1L), getShoppingCart(2L), getShoppingCart(3L));

        List<ShoppingCartDto> shoppingCartDtoList = mapper.toDtoList(shoppingCartList);

        assertNotNull(shoppingCartDtoList);
        assertEquals(shoppingCartList.size(), shoppingCartList.size());
        for (int i = 0; i < shoppingCartDtoList.size(); i++) {
            ShoppingCartDto dto = shoppingCartDtoList.get(i);
            ShoppingCart entity = shoppingCartList.get(i);
            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getUserId(), entity.getUser().getId());
            assertEquals(dto.getSelectedProducts().size(), entity.getSelectedProducts().size());
        }
    }

    @Test
    void should_map_shoppingCartDtoList_to_EntityList() {
        List<ShoppingCartDto> shoppingCartDtoList = List.of(getShoppingCartDto(1L), getShoppingCartDto(2L), getShoppingCartDto(3L));

        List<ShoppingCart> shoppingCartList = mapper.toEntityList(shoppingCartDtoList);

        assertNotNull(shoppingCartList);
        assertEquals(shoppingCartList.size(), shoppingCartList.size());
        for (int i = 0; i < shoppingCartList.size(); i++) {
            ShoppingCartDto dto = shoppingCartDtoList.get(i);
            ShoppingCart entity = shoppingCartList.get(i);
            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getUserId(), entity.getUser().getId());
            assertEquals(dto.getSelectedProducts().size(), entity.getSelectedProducts().size());
        }
    }

    @NotNull
    private ShoppingCart getShoppingCart(Long id) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(id);
        SelectedProduct selectedProduct = new SelectedProduct();
        selectedProduct.setId(id);
        shoppingCart.setSelectedProducts(Set.of(selectedProduct));
        User user = new User();
        user.setId(id);
        shoppingCart.setUser(user);
        return shoppingCart;
    }

    @NotNull
    private ShoppingCartDto getShoppingCartDto(Long id) {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setId(id);
        shoppingCartDto.setUserId(id);
        SelectedProductDto selectedProductDto = new SelectedProductDto();
        selectedProductDto.setId(id);
        shoppingCartDto.setSelectedProducts(Set.of(selectedProductDto));
        return shoppingCartDto;
    }
}
