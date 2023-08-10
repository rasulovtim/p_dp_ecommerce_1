package com.gitlab.mapper;

import com.gitlab.dto.ShoppingCartDto;
import com.gitlab.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShoppingCartMapper {

    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    @Mapping(source = "userId", target = "user.id") // Map userId to user.id
    ShoppingCart toEntity(ShoppingCartDto shoppingCartDto);
}
