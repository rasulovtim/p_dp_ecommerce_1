package com.gitlab.dto;

import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.validation.constraints.NotNull;

@Data
@Setter
public class RoleDto {

    @ReadOnlyProperty
    private Long id;

    @NotNull(message = "Role name shouldn't be empty")
    private String roleName;
}