package com.gitlab.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


@AnalyzerDef(name = "customanalyzer",
        tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
        filters = {
                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                @TokenFilterDef(factory = StopFilterFactory.class),
                @TokenFilterDef(factory = SnowballPorterFilterFactory.class,
                        params = @Parameter(name = "language", value = "Russian")
                )
        }
)
@NamedEntityGraph(name = "Product.productImages",
        attributeNodes = @NamedAttributeNode("productImages"))
@Entity
@EqualsAndHashCode(exclude = {"productImages", "selectedProducts", "review"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
@Indexed
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
//    @Field(termVector = TermVector.YES)
//    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO,
            analyzer = @Analyzer(definition = "customanalyzer"))
    private String name;

    @Column(name = "stock_count")
    private Integer stockCount;

    @OneToMany(mappedBy = "someProduct")
    private Set<ProductImage> productImages;

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