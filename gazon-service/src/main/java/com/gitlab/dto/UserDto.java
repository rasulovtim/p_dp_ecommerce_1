package com.gitlab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.gitlab.model.Passport;
import com.gitlab.model.Role;
import com.gitlab.model.ShippingAddress;
import com.gitlab.model.User;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Data
@Setter

public class UserDto {
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private long id;

    @Size(min = 1, max = 255, message = "Length of User's email should be between 1 and 255 characters")
    @NotEmpty(message = "User's email should have at least one character")
    private String email;

    @Size(min = 1, max = 255, message = "Length of User's password should be between 1 and 255 characters")
    @NotEmpty(message = "User's password should have at least one character")
    private String password;

    @Size(min = 1, max = 255, message = "Length of User's securityQuestion should be between 1 and 255 characters")
    @NotEmpty(message = "User's securityQuestion should have at least one character")
    private String securityQuestion;

    @Size(min = 1, max = 255, message = "Length of User's answerQuestion should be between 1 and 255 characters")
    @NotEmpty(message = "User's answerQuestion should have at least one character")
    private String answerQuestion;

    @Size(min = 1, max = 255, message = "Length of User's firstName should be between 1 and 255 characters")
    @NotEmpty(message = "User's firstName should have at least one character")
    private String firstName;

    @Size(min = 1, max = 255, message = "Length of User's lastName should be between 1 and 255 characters")
    @NotEmpty(message = "User's lastName should have at least one character")
    private String lastName;

    @NotNull(message = "User birthDate cannot be null")
    private LocalDate birthDate;

    @NotNull(message = "User gender cannot be null")
    private User.Gender gender;

    @Size(min = 1, max = 255, message = "Length of User's phoneNumber should be between 1 and 255 characters")
    @NotEmpty(message = "User's phoneNumber should have at least one character")
    private String phoneNumber;

    @NotNull(message = "User passport cannot be null")
    private Passport passport;

    @NotNull(message = "User shippingAddress cannot be null")
    private Set<ShippingAddress> shippingAddress;

    @NotNull(message = "User personalAddress cannot be null")
    private Set<PersonalAddressDto> personalAddress;

    @NotNull(message = "User pickupPointDto cannot be null")
    private Set<PickupPointDto> pickupPoint;

    @NotNull(message = "User postomatDto cannot be null")
    private Set<PostomatDto> postomat;

    @NotNull(message = "User bankCards cannot be null")
    private Set<BankCardDto> bankCards;

    @NotNull(message = "User roles cannot be null")
    private Set<Role> roles;
}
