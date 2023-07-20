package com.gitlab.mapper;

import com.gitlab.controller.AbstractIntegrationTest;
import com.gitlab.dto.ProductImageDto;
import com.gitlab.model.Product;
import com.gitlab.model.ProductImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProductImageMapperTest extends AbstractIntegrationTest {

    @Autowired
    private ProductImageMapper mapper;


    @Test
    void should_map_productImage_to_Dto() {
        ProductImage productImage = new ProductImage();
        productImage.setSomeProduct(new Product());
        productImage.setName("product1");
        productImage.setData(new byte[1]);

        ProductImageDto dtoTwin = mapper.toDto(productImage);

        assertNotNull(dtoTwin);
        assertEquals(productImage.getId(), dtoTwin.getId());
        assertEquals(productImage.getSomeProduct().getId(), dtoTwin.getProductId());
        assertEquals(productImage.getName(), dtoTwin.getName());
        assertEquals(Arrays.toString(productImage.getData()), Arrays.toString(dtoTwin.getData()));
    }

    @Test
    void should_map_productImageDto_to_Entity() {
        Product product = new Product();
        product.setId(1L);

        ProductImageDto productImageDto = new ProductImageDto();
        productImageDto.setProductId(1L);
        productImageDto.setName("product1");
        productImageDto.setData(new byte[1]);

        ProductImage entityTwin = mapper.toEntity(productImageDto);

        assertNotNull(entityTwin);
        assertEquals(productImageDto.getId(), entityTwin.getId());
        assertEquals(productImageDto.getProductId(), entityTwin.getSomeProduct().getId());
        assertEquals(productImageDto.getName(), entityTwin.getName());
        assertEquals(Arrays.toString(productImageDto.getData()), Arrays.toString(entityTwin.getData()));
    }

}
