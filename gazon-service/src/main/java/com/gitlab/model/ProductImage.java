package com.gitlab.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "someProduct")
@Table(name = "product_image")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product someProduct;

    @Column(name = "name")
    private String name;

    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "data", columnDefinition = "bytea")
    private byte[] data;
}
