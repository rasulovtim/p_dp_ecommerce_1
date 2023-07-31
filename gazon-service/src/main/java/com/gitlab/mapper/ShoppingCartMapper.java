package com.gitlab.mapper;

import com.gitlab.dto.ShoppingCartDto;
import com.gitlab.model.ShoppingCart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShoppingCartMapper {

    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    ShoppingCart toEntity(ShoppingCartDto shoppingCartDto);
}
