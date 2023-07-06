package com.gitlab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Setter
public class BankCardDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Size(min = 8, max = 19, message = "Length of BankCard's cardNumber should be between 8 and 19 characters")
    @NotEmpty(message = "BankCard's cardNumber should have at least eight characters")
    private String cardNumber;

    @NotNull(message = "BankCard's dueDate should not be empty")
    LocalDate dueDate;

    @NotNull(message = "BankCard's securityCode should not be empty")
    Byte securityCode;

}
