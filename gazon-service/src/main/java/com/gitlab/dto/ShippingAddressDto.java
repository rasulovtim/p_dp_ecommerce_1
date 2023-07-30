package com.gitlab.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PersonalAddressDto.class, name = "PersonalAddressDto"),
        @JsonSubTypes.Type(value = PostomatDto.class, name = "PostomatDto"),
        @JsonSubTypes.Type(value = PickupPointDto.class, name = "PickupPointDto")
})
public class ShippingAddressDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String address;

    private String directions;

}
