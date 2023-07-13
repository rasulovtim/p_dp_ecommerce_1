package com.gitlab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class ProductDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotEmpty(message = "Product's name should have at least three characters")
    @Size(min = 3, max = 60, message = "Length of Product's name should be between 5 and 60 characters")
    private String name;

    @NotNull(message = "Product's stockCount should not be empty")
    private Integer stockCount;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long[] imagesId;

    @NotEmpty(message = "Product's description should not be empty")
    private String description;

    @NotNull(message = "Product's isAdult field should not be empty")
    private Boolean isAdult;

    @NotEmpty(message = "Product's code should not be empty")
    private String code;

    @NotNull(message = "Product's weight should not be empty")
    private Long weight;

    @NotNull(message = "Product's price should not be empty")
    private BigDecimal price;

}
