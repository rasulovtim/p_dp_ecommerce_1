package com.gitlab.model;

import lombok.*;

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
    private Long id;

    @NotEmpty
    @Size(min = 2, max = 12)
    @Enumerated(EnumType.STRING)
    @Column(name = "citizenship")
    private Citizenship citizenship;

    @NotEmpty
    @Size(min = 2, max = 15)
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty
    @Size(min = 2, max = 25)
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty
    @Column(name = "patronym")
    private String patronym;

    @NotNull
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @NotNull
    @Column(name = "issue_date")
    private LocalDate issueDate;

    @NotEmpty
    @Column(name = "passport_number")
    private String passportNumber;

    @NotEmpty
    @Column(name = "issuer")
    private String issuer;

    @NotEmpty
    @Column(name = "issuer_number")
    private String issuerNumber;

    @AllArgsConstructor
    @Getter
    @ToString
    enum Citizenship {
        UKRAINE("Украина"), BELARUS("Беларусь"), RUSSIA("Россия"), KAZAKHSTAN("Казахстан"), AZERBAIJAN("Азербайджан"),
        ARMENIA("Армения"), GEORGIA("Грузия"), MOLDOVA("Молдова"), TAJIKISTAN("Таджикистан"),
        TURKMENISTAN("Туркменистан"), UZBEKISTAN("Узбекистан"), KYRGYZSTAN("Кыргызстан");

        private final String citizenshipInRussia;

    }

}
