package com.gitlab.mapper;

import com.gitlab.dto.ProductImageDto;
import com.gitlab.model.Product;
import com.gitlab.model.ProductImage;
import com.gitlab.repository.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ProductImageMapper {

    @Autowired
    private ProductRepository productRepository;

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

        return productRepository.findById(productId).orElse(null);
    }

    public List<ProductImageDto> toDtoList(List<ProductImage> productImageList) {
        return productImageList.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<ProductImage> toEntityList(List<ProductImageDto> productImageDtoList) {
        return productImageDtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
