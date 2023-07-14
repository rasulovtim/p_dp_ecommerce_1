package com.gitlab.service;


import com.gitlab.model.PickupPoint;
import com.gitlab.repository.PickupPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PickupPointService {

    private final PickupPointRepository pickupPointRepository;

    public List<PickupPoint> findAll() {
        return pickupPointRepository.findAll();
    }

    public Optional<PickupPoint> findById(Long id) {
        return pickupPointRepository.findById(id);
    }

    public PickupPoint save(PickupPoint pickupPoint) {
        return pickupPointRepository.save(pickupPoint);
    }

    public Optional<PickupPoint> update(Long id, PickupPoint pickupPoint) {
        Optional<PickupPoint> optionalSavedPickupPoint = findById(id);
        PickupPoint savedPickupPoint;
        if (optionalSavedPickupPoint.isEmpty()) {
            return optionalSavedPickupPoint;
        } else {
            savedPickupPoint = optionalSavedPickupPoint.get();
        }

        if (pickupPoint.getDirections() != null) {
            savedPickupPoint.setDirections(pickupPoint.getDirections());
        }
        if (pickupPoint.getPickupPointFeatures() != null) {
            savedPickupPoint.setPickupPointFeatures(pickupPoint.getPickupPointFeatures());
        }
        if (pickupPoint.getAddress() != null) {
            savedPickupPoint.setAddress(pickupPoint.getAddress());
        }
        if (pickupPoint.getShelfLifeDays() != null) {
            savedPickupPoint.setShelfLifeDays(pickupPoint.getShelfLifeDays());
        }

        return Optional.of(pickupPointRepository.save(savedPickupPoint));
    }

    public Optional<PickupPoint> delete(Long id) {
        Optional<PickupPoint> optionalSavedPickupPoint = findById(id);
        if (optionalSavedPickupPoint.isPresent()) {
            pickupPointRepository.deleteById(id);
        }
        return optionalSavedPickupPoint;
    }
}
