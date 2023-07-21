package com.gitlab.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@NamedEntityGraph(name = "Review.reviewImages",
        attributeNodes = @NamedAttributeNode("reviewImages"))
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "pros")
    private String pros;

    @Column(name = "cons")
    private String cons;

    @Column(name = "comment")
    private String comment;

    @OneToMany(mappedBy = "review")
    private Set<ReviewImage> reviewImages;

    @Column(name = "rating")
    private Byte rating;

    @Column(name = "helpful_counter")
    private Integer helpfulCounter;

    @Column(name = "not_helpful_counter")
    private Integer notHelpfulCounter;
}
