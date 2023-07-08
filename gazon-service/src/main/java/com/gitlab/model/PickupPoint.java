package com.gitlab.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pickup_point")
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


    @AllArgsConstructor
    @Getter
    @ToString
    public enum PickupPointFeatures {
        TRY_ON_CLOTHES("Примерка одежды"),
        TRY_ON_SHOES("Примерка обуви"),
        DELIVERY_FOR_BUSINESSES("Доставка для юридических лиц"),
        PARTIAL_ORDER_REDEMPTION("Частичный выкуп заказа"),
        PRODUCT_RETURNS("Возврат товаров");

        private final String pickupPointFeatureInRussian;
    }
}
