package com.gitlab.model;

import lombok.*;

import java.time.LocalDate;
import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Getter
@Setter
@Table(name = "bank_card",schema = "public", catalog = "postgres")
public class BankCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "security_code")
    private Integer securityCode;

}
