package com.gitlab.repository;

import com.gitlab.model.PickupPoint;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PickupPointRepository extends JpaRepository<PickupPoint, Long> {

    @Override
    @NonNull
    @EntityGraph(value = "PickupPoint.pickupPointFeatures")
    List<PickupPoint> findAll();

    @Override
    @NonNull
    @EntityGraph(value = "PickupPoint.pickupPointFeatures")
    Optional<PickupPoint> findById(@NonNull Long id);
}