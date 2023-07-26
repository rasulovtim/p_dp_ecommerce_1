package com.gitlab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class SelectedProductDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "SelectedProduct's productId should not be empty")
    private Long productId;

    @NotNull(message = "SelectedProduct's count should not be empty")
    @Range(min = 1, max = 2147483333, message = "SelectedProduct's count should be between 1 and 2147483333")
    private Integer count;

    @DecimalMin(value = "0", message = "SelectedProduct's sum should be between 0 and 9223372036854775000")
    @DecimalMax(value = "9223372036854775000", message = "SelectedProduct's sum should be between 0 and 9223372036854775000")
    @Digits(integer=20, fraction=3, message = "SelectedProduct sum's fractional part should not exceed 3 digits")
    private BigDecimal sum;

    @Range(min = 0, max = Long.MAX_VALUE - 807L,
            message = "SelectedProduct's totalWeight should be between 0 and 9223372036854775000")
    private Long totalWeight;
}