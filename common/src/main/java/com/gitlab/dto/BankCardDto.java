package com.gitlab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankCardDto {

    @ReadOnlyProperty
    private Long id;

    @Pattern(regexp = "^[1-9][0-9]*$", message = "cardNumber must contain only positive digits")
    @Size(min = 8, max = 19, message = "Length of BankCard's cardNumber should be between 8 and 19 positive digits")
    @NotEmpty(message = "BankCard's cardNumber should have at least eight characters")
    private String cardNumber;

    @NotNull(message = "BankCard's dueDate should not be empty")
    private LocalDate dueDate;

    @Range(min = 100, max = 9999, message = "Length of BankCard's securityCode should be between 3 and 4 positive digits")
    @NotNull(message = "BankCard's securityCode should not be empty")
    private Integer securityCode;

}