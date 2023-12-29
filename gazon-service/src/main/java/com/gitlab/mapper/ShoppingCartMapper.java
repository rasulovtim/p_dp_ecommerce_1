package com.gitlab.mapper;

import com.gitlab.dto.SelectedProductDto;
import com.gitlab.dto.ShoppingCartDto;
import com.gitlab.model.SelectedProduct;
import com.gitlab.model.ShoppingCart;
import com.gitlab.service.ProductService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {SelectedProductMapper.class, ProductService.class})
public abstract class ShoppingCartMapper {

    @Autowired
    protected SelectedProductMapper selectedProductMapper;

    @Autowired
    protected ProductService productService;

    @Mapping(source = "user.id", target = "userId")
    public abstract ShoppingCartDto toDto(ShoppingCart shoppingCart);

    @Mapping(source = "userId", target = "user.id")
    public abstract ShoppingCart toEntity(ShoppingCartDto shoppingCartDto);

    @AfterMapping
    protected void updateSelectedProducts(ShoppingCartDto dto, @MappingTarget ShoppingCart entity) {
        if (dto.getSelectedProducts() != null && !dto.getSelectedProducts().isEmpty()) {
            Set<SelectedProduct> selectedProducts = toSelectedProductSet(dto.getSelectedProducts());
            entity.setSelectedProducts(selectedProducts);
        } else {
            entity.setSelectedProducts(null);
        }
    }

    protected abstract Set<SelectedProduct> toSelectedProductSet(Set<SelectedProductDto> selectedProducts);

    public List<ShoppingCartDto> toDtoList(List<ShoppingCart> shoppingCartList) {
        return shoppingCartList.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<ShoppingCart> toEntityList(List<ShoppingCartDto> shoppingCartDtoList) {
        return shoppingCartDtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
}