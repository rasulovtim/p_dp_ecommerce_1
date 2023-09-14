package com.gitlab.dto;

import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Setter
public class ProductDto {

    @ReadOnlyProperty
    private Long id;

    @Size(min = 1, max = 255, message = "Length of Product's name should be between 1 and 255 characters")
    @NotEmpty(message = "Product's name should have at least one character")
    private String name;

    @Size(max = 1000, message = "Description should not exceed 1000 characters")
    private String description;

    private BigDecimal price;
}