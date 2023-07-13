package com.gitlab.mapper;

import com.gitlab.dto.ProductDto;
import com.gitlab.model.Product;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProductMapperTest {

    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);


    @Test
    void should_map_product_to_Dto() {
        Product product = new Product();
        product.setName("product1");
        product.setStockCount(1);
        product.setDescription("product1");
        product.setIsAdult(true);
        product.setCode("product1");
        product.setWeight(1L);
        product.setPrice(BigDecimal.ONE);

        ProductDto dtoTwin = mapper.toDto(product);

        assertNotNull(dtoTwin);
        assertEquals(product.getId(), dtoTwin.getId());
        assertEquals(product.getName(), dtoTwin.getName());
        assertEquals(product.getStockCount(), dtoTwin.getStockCount());
        assertEquals(product.getDescription(), dtoTwin.getDescription());
        assertEquals(product.getIsAdult(), dtoTwin.getIsAdult());
        assertEquals(product.getCode(), dtoTwin.getCode());
        assertEquals(product.getWeight(), dtoTwin.getWeight());
        assertEquals(product.getPrice(), dtoTwin.getPrice());
    }

    @Test
    void should_map_ProductDto_to_Entity() {
        ProductDto productDto = new ProductDto();
        productDto.setName("product1");
        productDto.setStockCount(1);
        productDto.setDescription("product1");
        productDto.setIsAdult(true);
        productDto.setCode("product1");
        productDto.setWeight(1L);
        productDto.setPrice(BigDecimal.ONE);

        Product entityTwin = mapper.toEntity(productDto);

        assertNotNull(entityTwin);
        assertEquals(productDto.getId(), entityTwin.getId());
        assertEquals(productDto.getName(), entityTwin.getName());
        assertEquals(productDto.getStockCount(), entityTwin.getStockCount());
        assertEquals(productDto.getDescription(), entityTwin.getDescription());
        assertEquals(productDto.getIsAdult(), entityTwin.getIsAdult());
        assertEquals(productDto.getCode(), entityTwin.getCode());
        assertEquals(productDto.getWeight(), entityTwin.getWeight());
        assertEquals(productDto.getPrice(), entityTwin.getPrice());
    }

}
