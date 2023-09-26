package com.gitlab.mapper;

import com.gitlab.dto.OrderDto;
import com.gitlab.model.Order;
import com.gitlab.model.SelectedProduct;
import com.gitlab.model.User;
import com.gitlab.service.SelectedProductService;
import com.gitlab.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class OrderMapper {

    @Autowired
    private UserService userService;

    @Autowired
    private SelectedProductService selectedProductService;


    @Mapping(source = "user", target = "userId")
    public abstract OrderDto toDto(Order order);

    public Long mapUserToUserId(User user) {
        if (user == null) {
            return null;
        }
        return user.getId();
    }

    @Mapping(source = "userId", target = "user")
    public abstract Order toEntity(OrderDto orderDto);

    public User mapUserIdToUser(Long userId) {
        if (userId == null) {
            return null;
        }
        return userService.findById(userId).orElse(null);
    }

    public Long[] mapSelectedProductsToSelectedProductId(Set<SelectedProduct> selectedProducts) {
        if (selectedProducts == null || selectedProducts.isEmpty()) {
            return new Long[0];
        }
        return selectedProducts.stream().map(SelectedProduct::getId).toArray(Long[]::new);
    }

    public Set<SelectedProduct> mapSelectedProductIdTOSelectedProducts(Long[] selectedProductId) {

        if (selectedProductId == null || selectedProductId.length == 0) {
            return null;
        }
        return Arrays.stream(selectedProductId).map(selectedProductService::findById).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());
    }


}
