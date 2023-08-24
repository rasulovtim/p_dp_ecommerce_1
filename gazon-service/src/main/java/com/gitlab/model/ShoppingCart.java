package com.gitlab.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = "selectedProducts")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shopping_cart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "shoppingCart", fetch = FetchType.EAGER)
    private Set<SelectedProduct> selectedProducts;
}