package com.gitlab.mapper;

import com.gitlab.dto.ProductImageDto;
import com.gitlab.model.Product;
import com.gitlab.model.ProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ProductImageMapper {

    @PersistenceContext
    private EntityManager entityManager;

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

        return entityManager.find(Product.class, productId);
    }
}