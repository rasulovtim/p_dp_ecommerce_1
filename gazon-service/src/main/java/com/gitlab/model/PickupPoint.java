package com.gitlab.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pickup_point")
public class PickupPoint extends ShippingAddress {

    @Column(name = "shelf_life_days")
    private Byte shelfLifeDays;

    @ManyToMany
    @JoinTable(
            name = "pickup_points-pickup_points_features",
            joinColumns = @JoinColumn(name = "pickup_point_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    private Set<PickupPointFeature> pickupPointFeatures;


    @Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = "pickup_point_feature")
    public static class PickupPointFeature {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "name")
        private String name;
    }
}
