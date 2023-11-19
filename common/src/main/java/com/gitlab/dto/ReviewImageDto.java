package com.gitlab.dto;

import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Setter
public class ReviewImageDto {

    @ReadOnlyProperty
    private Long id;

    @NotNull(message = "ReviewImage's reviewId should not be empty")
    private Long reviewId;

    @NotEmpty(message = "ReviewImage's name should not be empty")
    @Size(max = 256, message = "Length of ReviewImage's name should be between 1 and 256 characters")
    private String name;

    @NotNull(message = "ReviewImage's data should not be empty")
    private byte[] data;
}
