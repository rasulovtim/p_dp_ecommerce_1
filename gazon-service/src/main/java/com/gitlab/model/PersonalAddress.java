package com.gitlab.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "personal_address")
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