package com.gitlab.model;

import com.gitlab.enums.EntityStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Сущность - пример
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "example")
public class Example {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "example_text")
    private String exampleText;

    @Column(name = "entity_status")
    @Enumerated(EnumType.STRING)
    private EntityStatus entityStatus;

    //добавил потому что тесты не проходят
    public Example(Long id, String exampleText) {
        this.id = id;
        this.exampleText = exampleText;
        entityStatus = EntityStatus.ACTIVE;
    }
}