package com.gitlab.mapper;

import com.gitlab.controller.AbstractIntegrationTest;
import com.gitlab.dto.SelectedProductDto;
import com.gitlab.model.Product;
import com.gitlab.model.SelectedProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SelectedProductMapperTest extends AbstractIntegrationTest {

    @Autowired
    SelectedProductMapper selectedProductMapper;

    @Test
    void should_map_productImage_to_Dto() {
        SelectedProduct selectedProduct = new SelectedProduct();
        selectedProduct.setProduct(new Product());
        selectedProduct.setCount(2);

        SelectedProductDto dtoTwin = selectedProductMapper.toDto(selectedProduct);

        assertNotNull(dtoTwin);
        assertEquals(selectedProduct.getId(), dtoTwin.getId());
        assertEquals(selectedProduct.getProduct().getId(), dtoTwin.getProductId());
        assertEquals(selectedProduct.getCount(), dtoTwin.getCount());
    }

    @Test
    void should_map_productImageDto_to_Entity() {
        Product product = new Product();
        product.setId(1L);

        SelectedProductDto selectedProductDto = new SelectedProductDto();
        selectedProductDto.setProductId(1L);
        selectedProductDto.setCount(1);

        SelectedProduct entityTwin = selectedProductMapper.toEntity(selectedProductDto);

        assertNotNull(entityTwin);
        assertEquals(selectedProductDto.getId(), entityTwin.getId());
        assertEquals(selectedProductDto.getProductId(), entityTwin.getProduct().getId());
        assertEquals(selectedProductDto.getCount(), entityTwin.getCount());
    }
}
