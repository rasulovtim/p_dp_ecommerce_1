package com.gitlab.mapper;

import com.gitlab.controller.AbstractIntegrationTest;
import com.gitlab.dto.SelectedProductDto;
import com.gitlab.model.Product;
import com.gitlab.model.SelectedProduct;
import com.gitlab.model.User;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SelectedProductMapperTest extends AbstractIntegrationTest {

    @Autowired
    SelectedProductMapper mapper;

    @Test
    void should_map_productImage_to_Dto() {
        SelectedProduct selectedProduct = getSelectedProduct(1L);

        SelectedProductDto dtoTwin = mapper.toDto(selectedProduct);

        assertNotNull(dtoTwin);
        assertEquals(selectedProduct.getId(), dtoTwin.getId());
        assertEquals(selectedProduct.getProduct().getId(), dtoTwin.getProductId());
        assertEquals(selectedProduct.getUser().getId(), dtoTwin.getUserId());
        assertEquals(selectedProduct.getCount(), dtoTwin.getCount());
    }

    @Test
    void should_map_productImageDto_to_Entity() {
        SelectedProductDto selectedProductDto = getSelectedProductDto(1L);

        SelectedProduct entityTwin = mapper.toEntity(selectedProductDto);

        assertNotNull(entityTwin);
        assertEquals(selectedProductDto.getId(), entityTwin.getId());
        assertEquals(selectedProductDto.getProductId(), entityTwin.getProduct().getId());
        assertEquals(selectedProductDto.getUserId(), entityTwin.getUser().getId());
        assertEquals(selectedProductDto.getCount(), entityTwin.getCount());
    }

    @Test
    void should_map_selectedProductList_to_DtoList() {
        List<SelectedProduct> selectedProductList = List.of(getSelectedProduct(1L), getSelectedProduct(2L), getSelectedProduct(3L));

        List<SelectedProductDto> selectedProductDtoList = mapper.toDtoList(selectedProductList);

        assertNotNull(selectedProductDtoList);
        assertEquals(selectedProductList.size(), selectedProductList.size());
        for (int i = 0; i < selectedProductDtoList.size(); i++) {
            SelectedProductDto dto = selectedProductDtoList.get(i);
            SelectedProduct entity = selectedProductList.get(i);
            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getProductId(), entity.getProduct().getId());
            assertEquals(dto.getUserId(), entity.getUser().getId());
            assertEquals(dto.getCount(), entity.getCount());
        }
    }

    @Test
    void should_map_selectedProductDtoList_to_EntityList() {
        List<SelectedProductDto> selectedProductDtoList = List.of(getSelectedProductDto(1L), getSelectedProductDto(2L), getSelectedProductDto(3L));

        List<SelectedProduct> selectedProductList = mapper.toEntityList(selectedProductDtoList);

        assertNotNull(selectedProductList);
        assertEquals(selectedProductList.size(), selectedProductList.size());
        for (int i = 0; i < selectedProductList.size(); i++) {
            SelectedProductDto dto = selectedProductDtoList.get(i);
            SelectedProduct entity = selectedProductList.get(i);
            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getProductId(), entity.getProduct().getId());
            assertEquals(dto.getUserId(), entity.getUser().getId());
            assertEquals(dto.getCount(), entity.getCount());
        }
    }

    @NotNull
    private SelectedProduct getSelectedProduct(Long id) {
        SelectedProduct selectedProduct = new SelectedProduct();
        selectedProduct.setId(id);
        Product product = new Product();
        product.setId(id);
        selectedProduct.setProduct(product);
        User user = new User();
        user.setId(id);
        selectedProduct.setUser(user);
        selectedProduct.setCount((int) (id - 1));
        return selectedProduct;
    }

    @NotNull
    private SelectedProductDto getSelectedProductDto(Long id) {
        SelectedProductDto selectedProductDto = new SelectedProductDto();
        selectedProductDto.setId(id);
        selectedProductDto.setProductId(id);
        selectedProductDto.setUserId(id);
        selectedProductDto.setCount((int) (id - 1));
        return selectedProductDto;
    }
}
