package com.gitlab.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Size(min = 1, max = 50, message = "Length of Role's name should be between 1 and 50 characters")
    @NotEmpty(message = "Role's name should have at least one character")
    private String name;

}
