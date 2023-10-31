package com.gitlab.dto;

import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Setter
public class ProductImageDto {

    @ReadOnlyProperty
    private Long id;

    @NotNull(message = "ProductImage's productId should not be empty")
    private Long productId;

    @NotEmpty(message = "ProductImage's name should not be empty")
    @Size(max = 256, message = "Length of ProductImage's name should be between 1 and 256 characters")
    private String name;

    @NotNull(message = "ProductImage's data should not be empty")
    private byte[] data;

}
