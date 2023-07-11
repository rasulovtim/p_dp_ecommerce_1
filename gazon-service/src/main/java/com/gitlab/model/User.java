package com.gitlab.model;

import com.gitlab.model.enums.Gender;
import lombok.*;

import java.time.LocalDate;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Getter
@Setter
@Table(name = "users", schema = "public", catalog = "postgres")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "security_question")
    private String securityQuestion;

    @Column(name = "answer_question")
    private String answerQuestion;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "id")
    private Passport passport;

    @Column(name = "create_date")
    private LocalDate createDate;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "id")
    private Set<BankCard> bankCards;


    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "id")
    private Set<ShippingAddress> shippingAddress;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "id")
    private Set<Role> roles;

//    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    @JoinTable(name = "users_bank_cards",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "bank_cards_id"))
//    private Set<BankCard> bankCards;
//
//
//    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
//    @JoinTable(name = "users_shipping_address",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "shipping_address_id"))
//        private Set<ShippingAddress> shippingAddress;
//
//    @OneToMany(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
//    @JoinTable(name = "users_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "roles_id"))
//    private Set<Role> roles;

}
