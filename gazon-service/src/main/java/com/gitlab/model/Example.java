package com.gitlab.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Сущность - пример
 */
@Data
@Entity
@Table(name = "example")
public class Example {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "example_text")
    // добавить unique constraint
    // добавить валидацию длины в дто
    private String exampleText;
}
