package com.gitlab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class SelectedProductDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;


    @NotNull(message = "SelectedProduct's productId should not be empty")
    private Long productId;

    @NotNull(message = "SelectedProduct's count should not be empty")
    @Range(min = 1, max = 2147483333, message = "SelectedProduct's count should be between 1 and 2147483333")
    private Integer count;

}
