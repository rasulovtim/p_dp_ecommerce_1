package com.gitlab.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private BankCard bankCard;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private PaymentStatus paymentStatus;

    @Column(name = "create_date_time")
    private LocalDateTime createDateTime;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Order order;

    @Column(name = "sum")
    private BigDecimal sum;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
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
