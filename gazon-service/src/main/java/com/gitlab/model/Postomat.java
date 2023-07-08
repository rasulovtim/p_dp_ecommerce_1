package com.gitlab.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "postomat")
public class Postomat extends ShippingAddress {

    @Column(name = "shelf_life_days")
    private Byte shelfLifeDays;
}
