package com.gitlab.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
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


    @AllArgsConstructor
    @Getter
    public enum PickupPointFeatures {
        TRY_ON_CLOTHES("Примерка одежды"),
        TRY_ON_SHOES("Примерка обуви"),
        DELIVERY_FOR_BUSINESSES("Доставка для юридических лиц"),
        PARTIAL_ORDER_REDEMPTION("Частичный выкуп заказа"),
        PRODUCT_RETURNS("Возврат товаров");

        private final String pickupPointFeatureInRussian;
    }
}