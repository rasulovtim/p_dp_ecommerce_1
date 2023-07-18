package com.gitlab.mapper;

import com.gitlab.dto.ProductImageDto;
import com.gitlab.model.Product;
import com.gitlab.model.ProductImage;
import com.gitlab.service.ProductService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ProductImageMapper {

    @Autowired
    protected ProductService productService;

    @Mapping(source = "someProduct", target = "productId")
    public abstract ProductImageDto toDto(ProductImage productImage);


    public Long mapProductToProductId(Product product) {
        if (product == null) {
            return null;
        }
        return product.getId();
    }

    @Mapping(source = "productId", target = "someProduct")
    public abstract ProductImage toEntity(ProductImageDto productImageDto);


    public Product mapProductIdToProduct(Long productId) {
        if (productId == null) {
            return null;
        }

        return productService.findById(productId).
                orElseThrow(() -> new RuntimeException("Product wasn't found"));
    }
}
