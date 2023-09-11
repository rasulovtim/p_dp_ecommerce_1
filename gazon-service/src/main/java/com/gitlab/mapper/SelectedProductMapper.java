package com.gitlab.mapper;

import com.gitlab.dto.SelectedProductDto;
import com.gitlab.model.Product;
import com.gitlab.model.SelectedProduct;
import com.gitlab.model.User;
import com.gitlab.service.ProductService;
import com.gitlab.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class SelectedProductMapper {

    @Autowired
    protected ProductService productService;

    @Autowired
    protected UserService userService;

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "user.id", target = "userId")
    public abstract SelectedProductDto toDto(SelectedProduct selectedProduct);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "userId", target = "user")
    public abstract SelectedProduct toEntity(SelectedProductDto selectedProductDto);

    public void calculatedUnmappedFields(SelectedProductDto selectedProductDto, SelectedProduct selectedProduct) {
        selectedProductDto.setSum(selectedProduct.getProduct().getPrice()
                .multiply(BigDecimal.valueOf(selectedProduct.getCount())));
        selectedProductDto.setTotalWeight(selectedProduct.getProduct().getWeight() * selectedProduct.getCount());
    }

    public Product mapProductIdToProduct(Long productId) {
        if (productId == null) {
            return null;
        }
        return productService.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product wasn't found"));
    }

    public User mapUserIdToUser(Long userId) {
        if (userId == null) {
            return null;
        }
        return userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("User wasn't found"));
    }
}