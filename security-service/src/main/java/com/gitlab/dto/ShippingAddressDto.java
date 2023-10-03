package com.gitlab.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.validation.constraints.Size;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PersonalAddressDto.class, name = "personalAddress"),
        @JsonSubTypes.Type(value = PostomatDto.class, name = "postomat"),
        @JsonSubTypes.Type(value = PickupPointDto.class, name = "pickupPoint")
})
public class ShippingAddressDto {

    @ReadOnlyProperty
    private Long id;
    @Size(min = 1, max = 255, message = "Length of address should be between 1 and 255 characters")
    private String address;
    @Size(min = 1, max = 255, message = "Length of directions should be between 1 and 255 characters")
    private String directions;

}
