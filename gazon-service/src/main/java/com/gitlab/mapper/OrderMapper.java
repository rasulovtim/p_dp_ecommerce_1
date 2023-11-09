package com.gitlab.mapper;

import com.gitlab.dto.OrderDto;
import com.gitlab.model.Order;
import com.gitlab.model.User;
import com.gitlab.service.UserService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {SelectedProductMapper.class, ShippingAddressMapper.class})
public abstract class OrderMapper {

    @Autowired
    protected SelectedProductMapper selectedProductMapper;

    @Autowired
    protected UserService userService;

    @Autowired
    protected UserMapper userMapper;

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "shippingAddress", target = "shippingAddressDto")
    public abstract OrderDto toDto(Order order);

    public User mapUserIdToUser(Long userId) {
        if (userId == null) {
            return null;
        }
        return userService.findById(userId)
                .map(userMapper::toEntity)
                .orElseThrow(() -> new RuntimeException("User wasn't found"));
    }

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "shippingAddressDto", target = "shippingAddress")
    public abstract Order toEntity(OrderDto orderDto);
}
