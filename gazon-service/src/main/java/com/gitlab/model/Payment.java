package com.gitlab.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(exclude = {"order", "user"})
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy="id", cascade = CascadeType.ALL)
    private Set<BankCard> bankCardsSet;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private PaymentStatus paymentStatus;

    @Column(name = "create_date_time")
    private LocalDateTime createDateTime;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Order order;

    @Column(name = "sum")
    private BigDecimal sum;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @AllArgsConstructor
    @Getter
    public enum PaymentStatus {
        NOT_PAID,
        PAID,
        OVERDUE,
        CANCELED
    }
}
