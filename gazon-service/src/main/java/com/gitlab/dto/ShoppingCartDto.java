package com.gitlab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

@Data
public class ShoppingCartDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "User ID should not be null.")
    private Long userId;

    @NotEmpty(message = "At least one selected product should be present.")
    @Size(min = 1, message = "At least one selected product should be present.")
    private Set<String> selectedProducts;

    @NotNull(message = "Sum should not be null.")
    private BigDecimal sum;

    @NotNull(message = "Total weight should not be null.")
    private Long totalWeight;


}
