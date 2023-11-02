package com.gitlab.model;

import com.gitlab.enums.PickupPointFeatures;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "pickup_point")
@NamedEntityGraph(name = "PickupPoint.pickupPointFeatures", attributeNodes = @NamedAttributeNode("pickupPointFeatures"))
public class PickupPoint extends ShippingAddress {

    @Column(name = "shelf_life_days")
    private Byte shelfLifeDays;

    @ElementCollection(targetClass = PickupPointFeatures.class)
    @CollectionTable(
            name = "pickup_point_features_to_pickup_point",
            joinColumns = @JoinColumn(name = "pickup_point_id")
    )
    @Column(name = "pickup_point_feature")
    @Enumerated(EnumType.STRING)
    private Set<PickupPointFeatures> pickupPointFeatures;
}