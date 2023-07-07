package com.gitlab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.*;

@Data
@Setter
public class PostomatDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Size(min = 1, max = 255, message = "Length of address should be between 1 and 255 characters")
    @NotEmpty(message = "Address can not be empty")
    private String address;

    @Size(max = 255, message = "Length of directions should not exceed 255 characters")
    private String directions;

    @NotNull(message = "Shelf life days can not be empty")
    @Max(value = 30, message = "Shelf life can not be more than 30 days")
    @Min(value = 1, message = "Shelf life should be at least 1 day")
    private Byte shelfLifeDays;
}
