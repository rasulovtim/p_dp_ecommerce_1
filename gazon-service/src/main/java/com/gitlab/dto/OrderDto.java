package com.gitlab.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.gitlab.model.Order;
import com.gitlab.model.ShippingAddress;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class OrderDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "Shipping address should not be null. Please provide a valid shipping address")
    ShippingAddress shippingAddress;

    @NotNull(message = "Shipping date should not be null. Please provide a valid shipping date")
    LocalDate shippingDate;

    @NotEmpty(message = "Order code should be empty. Please provide a valid order code")
    @Size(min = 1, max = 20)
    String orderCode;

    @NotNull(message = "Datetime of creation should not be null. Please provide a valid datetime")
    LocalDateTime createDateTime;

    @NotNull(message = "Sum should not be null. Please provide a valid sum")
    BigDecimal sum;

    @NotNull(message = "Discount should not be null. Please provide a valid discount")
    BigDecimal discount;

    @NotNull(message = "Bag counter should not be null. Please provide a valid bag counter")
    Byte bagCounter;

    @NotNull(message = "User ID should not be null. Please provide a valid user ID")
    Long userId;

    Set<SelectedProductDto> selectedProducts;

    Order.OrderStatus orderStatus;


}
