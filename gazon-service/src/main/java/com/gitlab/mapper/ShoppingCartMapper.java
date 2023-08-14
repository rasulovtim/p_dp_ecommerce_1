package com.gitlab.mapper;

import com.gitlab.dto.ShoppingCartDto;
import com.gitlab.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ShoppingCartMapper {

    @Autowired
    protected SelectedProductMapper selectedProductMapper;

    @Mapping(source = "user.id", target = "userId")
    public abstract ShoppingCartDto toDto(ShoppingCart shoppingCart);

    public abstract ShoppingCart toEntity(ShoppingCartDto shoppingCartDto);
}
