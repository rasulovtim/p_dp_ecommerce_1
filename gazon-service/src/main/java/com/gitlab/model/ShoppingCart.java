package com.gitlab.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> selectedProducts;

    public ShoppingCart() {
    }

    public ShoppingCart(Long id, User user, Set<String> selectedProducts) {
        this.id = id;
        this.user = user;
        this.selectedProducts = selectedProducts;
    }

    public Long getUserId() {
        if (user != null) {
            return user.getId();
        }
        return null;
    }


}
