package com.gitlab.mapper;

import com.gitlab.dto.SelectedProductDto;
import com.gitlab.model.Product;
import com.gitlab.model.SelectedProduct;
import com.gitlab.service.ProductService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class SelectedProductMapper {

    @Autowired
    protected ProductService productService;

    @Mapping(source = "product.id", target = "productId")
    public abstract SelectedProductDto toDto(SelectedProduct selectedProduct);

    public void calculatedUnmappedFields(SelectedProductDto selectedProductDto, SelectedProduct selectedProduct) {
        selectedProductDto.setSum(selectedProduct.getProduct().getPrice()
                .multiply(BigDecimal.valueOf(selectedProduct.getCount())));
        selectedProductDto.setTotalWeight(selectedProduct.getProduct().getWeight() * selectedProduct.getCount());
    }

    @Mapping(source = "productId", target = "product")
    public abstract SelectedProduct toEntity(SelectedProductDto selectedProductDto);

    public Product mapProductIdToProduct(Long productId) {
        if (productId == null) {
            return null;
        }
        return productService.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product wasn't found"));
    }
}
