package com.gitlab.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "passport")
@NoArgsConstructor
@AllArgsConstructor
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotEmpty
    @Size(min = 2, max = 12)
    @Enumerated(EnumType.STRING)
    @Column(name = "citizenship")
    Citizenship citizenship;

    @NotEmpty
    @Size(min = 2, max = 15)
    @Column(name = "first_name")
    String firstName;

    @NotEmpty
    @Size(min = 2, max = 25)
    @Column(name = "last_name")
    String lastName;

    @NotEmpty
    @Column(name = "patronym")
    String patronym;

    @NotNull
    @Column(name = "birth_date")
    LocalDate birthDate;

    @NotNull
    @Column(name = "issue_date")
    LocalDate issueDate;

    @NotEmpty
    @Column(name = "passport_number")
    String passportNumber;

    @NotEmpty
    @Column(name = "issuer")
    String issuer;

    @NotEmpty
    @Column(name = "issuer_number")
    String issuerNumber;

    enum Citizenship {
        УКРАИНА, БЕЛАРУСЬ, РОССИЯ, КАЗАХСТАН, АЗЕРБАЙДЖАН, АРМЕНИЯ, ГРУЗИЯ, МОЛДОВА, ТАДЖИКИСТАН, ТУРКМЕНИСТАН, УЗБЕКИСТАН, КЫРГЫЗСТАН

    }

}
