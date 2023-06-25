package com.gitlab.mapper;

import com.gitlab.dto.ProductDto;
import com.gitlab.model.Product;
import com.gitlab.model.ProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    @Mapping(source = "productImages", target = "imagesId")
    ProductDto toDto(Product example);

    default Long[] mapProductImagesToImagesId(Set<ProductImage> productImages) {
        if (productImages == null || productImages.isEmpty()) {
            return null;
        }
        return productImages.stream()
                .map(ProductImage::getId)
                .toArray(Long[]::new);
    }

//    Product toEntity(ProductDto exampleDto);
}
