package com.gitlab.model;

import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(exclude = {"order", "user"})
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "bank_card_id", referencedColumnName = "id")
    private BankCard bankCard;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "create_date_time")
    private LocalDateTime createDateTime;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Order order;

    @Column(name = "sum")
    private BigDecimal sum;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    @AllArgsConstructor
    @Getter
    public enum PaymentStatus {
        NOT_PAID,
        PAID,
        OVERDUE,
        CANCELED
    }
}
