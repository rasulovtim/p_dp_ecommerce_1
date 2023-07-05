package com.gitlab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Setter
public class PersonalAddressDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Size(min = 1, max = 255, message = "Length of address should be between 1 and 255 characters")
    @NotEmpty(message = "Address can not be empty")
    private String address;

    @Size(max = 255, message = "Length of directions should not exceed 255 characters")
    private String directions;

    @Size(min = 1, max = 255, message = "Length of apartment should be between 1 and 255 characters")
    @NotEmpty(message = "Apartment can not be empty")
    private String apartment;

    @Size(min = 1, max = 10, message = "Length of floor should be between 1 and 10 characters")
    @NotEmpty(message = "Floor can not be empty")
    private String floor;

    @Size(min = 1, max = 255, message = "Length of entrance should be between 1 and 255 characters")
    @NotEmpty(message = "Entrance can not be empty")
    private String entrance;

    @Size(max = 50, message = "Length of door code should not exceed 50 characters")
    private String doorCode;

    @Size(max = 12, message = "Length of post code should not exceed 12 characters")
    private String postCode;
}
