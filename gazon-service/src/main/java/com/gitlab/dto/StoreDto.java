package com.gitlab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class StoreDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private Set<Long> managersId;

    @NotNull(message = "Owner's dueDate should not be empty")
    private Long ownerId;
}

