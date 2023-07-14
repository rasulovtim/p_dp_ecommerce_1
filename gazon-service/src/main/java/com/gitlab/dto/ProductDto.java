package com.gitlab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class ProductDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotEmpty(message = "Product's name should not be empty")
    @Size(min = 3, max = 60, message = "Length of Product's name should be between 3 and 60 characters")
    private String name;

    @Range(min = 1, max = 2147483333, message = "Product's stockCount should be between 1 and 2147483333")
    @NotNull(message = "Product's stockCount should not be empty")
    private Integer stockCount;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long[] imagesId;

    @NotEmpty(message = "Product's description should not be empty")
    @Size(min = 3, max = 1000, message = "Length of Product's description should be between 3 and 600 characters")
    private String description;

    @NotNull(message = "Product's isAdult field should not be empty")
    private Boolean isAdult;

    @NotEmpty(message = "Product's code should not be empty")
    @Size(min = 2, max = 30, message = "Length of Product's code should be between 2 and 30 characters")
    private String code;

    @Range(min = 1, max = 2147483333, message = "Product's weight should be between 1 and 2147483333")
    @NotNull(message = "Product's weight should not be empty")
    private Long weight;

    @DecimalMin(value = "0.1", message = "Product's price should be between 0.1 and 2147483333")
    @DecimalMax(value = "2147483333", message = "Product's price should be between 0.1 and 2147483333")
    @NotNull(message = "Product's price should not be empty")
    private BigDecimal price;

}
