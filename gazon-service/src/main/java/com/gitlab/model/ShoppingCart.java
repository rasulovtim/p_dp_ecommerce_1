package com.gitlab.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ElementCollection
    private Set<String> selectedProducts;

    private BigDecimal sum;

    private Long totalWeight;

  
    public ShoppingCart() {
    }

    public ShoppingCart(Long id, User user, Set<String> selectedProducts, BigDecimal sum, Long totalWeight) {
        this.id = id;
        this.user = user;
        this.selectedProducts = selectedProducts;
        this.sum = sum;
        this.totalWeight = totalWeight;
    }
}
