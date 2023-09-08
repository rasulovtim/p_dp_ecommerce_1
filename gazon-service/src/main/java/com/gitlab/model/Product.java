package com.gitlab.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Set;

@NamedEntityGraph(name = "Product.productImages",
        attributeNodes = @NamedAttributeNode("productImages"))
@Entity
@EqualsAndHashCode(exclude = {"productImages", "selectedProducts", "review"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "stock_count")
    private Integer stockCount;

    @OneToMany(mappedBy = "someProduct")
    private Set<ProductImage> productImages;

    @OneToMany(mappedBy = "product")
    private Set<SelectedProduct> selectedProducts;

    @OneToMany(mappedBy = "product")
    private Set<Review> review;

    @Column(name = "description")
    private String description;

    @Column(name = "is_adult")
    private Boolean isAdult;

    @Column(name = "code")
    private String code;

    @Column(name = "weight")
    private Long weight;

    @Column(name = "price")
    private BigDecimal price;

}
