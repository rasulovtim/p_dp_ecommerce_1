package com.gitlab.model;

import com.gitlab.enums.OrderStatus;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"shippingAddress", "user", "selectedProducts"})
@ToString(callSuper = true)
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "shipping_address_id", referencedColumnName = "shipping_address_id")
    ShippingAddress shippingAddress;

    @Column(name = "shipping_date")
    LocalDate shippingDate;

    @Column(name = "order_code")
    String orderCode;

    @Column(name = "create_date_time")
    LocalDateTime createDateTime;

    @Column(name = "sum")
    BigDecimal sum;

    @Column(name = "discount")
    BigDecimal discount;

    @Column(name = "bag_counter")
    Byte bagCounter;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    Set<SelectedProduct> selectedProducts;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    OrderStatus orderStatus;

    public Order(long id) {
        this.id = id;
    }

    public Order(long id, String orderCode) {
        this.id = id;
        this.orderCode = orderCode;
    }
}
