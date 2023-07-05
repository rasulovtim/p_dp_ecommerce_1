package com.gitlab.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "personal_address")
public class PersonalAddress extends ShippingAddress {

    @Column(name = "apartment")
    private String apartment;

    @Column(name = "floor")
    private String floor;

    @Column(name = "entrance")
    private String entrance;

    @Column(name = "door_code")
    private String doorCode;

    @Column(name = "post_code")
    private String postCode;
}
