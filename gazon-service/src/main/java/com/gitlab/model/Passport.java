package com.gitlab.model;

import com.gitlab.enums.Citizenship;
import lombok.*;

import javax.persistence.*;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "citizenship")
    private Citizenship citizenship;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "patronym")
    private String patronym;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "issuer")
    private String issuer;

    @Column(name = "issuer_number")
    private String issuerNumber;
}