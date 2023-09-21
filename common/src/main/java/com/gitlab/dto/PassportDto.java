package com.gitlab.dto;

import com.gitlab.model.Passport;
import lombok.*;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PassportDto {

    @ReadOnlyProperty
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Passport's citizenship shouldn't be empty")
    private Passport.Citizenship citizenship;

    @NotEmpty(message = "Passport's first name shouldn't be empty")
    @Size(min = 2, max = 15, message = "Passport's first name should have at least two characters and not exceed 15")
    @Pattern(regexp = "[а-яёa-zА-ЯЁA-Z-]+", message = "first name can contain only letters")
    private String firstName;

    @Pattern(regexp = "[а-яёa-zА-ЯЁA-Z-]+", message = "last name can contain only letters")
    @NotEmpty(message = "Passport's lastname shouldn't be empty")
    @Size(min = 2, max = 25, message = "Passport's lastname should have at least two characters and not exceed 25")
    private String lastName;

    @Pattern(regexp = "[а-яёa-zА-ЯЁA-Z-]+", message = "patronym can contain only letters")
    @Size(min = 2, max = 25, message = "Passport patronym should have at least two characters and not exceed 25")
    private String patronym;

    @NotNull(message = "Passport's birth date shouldn't be empty")
    private LocalDate birthDate;

    @NotNull(message = "Passport's issue date shouldn't be empty")
    private LocalDate issueDate;

    @Pattern(regexp = "^\\d{4}\\s\\d{6}$", message = "Passport issuer number must be in \"**** ******\" format")
    @NotEmpty(message = "Passport number shouldn't be empty")
    @Size(min = 11, max = 11, message = "Passport number must consist of 11 characters")
    private String passportNumber;

    @NotEmpty(message = "Passport's issuer shouldn't be empty")
    @Size(min = 10, max = 255, message = "Passport's issuer cannot contain less than ten characters and should not exceed 255")
    private String issuer;

    @Pattern(regexp = "^\\d{3}-\\d{3}$", message = "Passport's issuer number must be in \"***-***\" format")
    @NotEmpty(message = "Passport's issuer number shouldn't be empty")
    @Size(min = 7, max = 7, message = "Passport number must consist of 7 characters")
    private String issuerNumber;

}