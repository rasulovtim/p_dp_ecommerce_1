package com.gitlab.service;


import com.gitlab.dto.PickupPointDto;
import com.gitlab.mapper.PickupPointMapper;
import com.gitlab.model.PickupPoint;
import com.gitlab.repository.PickupPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PickupPointService {

    private final PickupPointRepository pickupPointRepository;

    private final PickupPointMapper pickupPointMapper;

    public List<PickupPoint> findAll() {
        return pickupPointRepository.findAll();
    }

    public List<PickupPointDto> findAllDto() {
        List<PickupPoint> pickupPoints = pickupPointRepository.findAll();
        return pickupPoints.stream()
                .map(pickupPointMapper::toDto)
                .collect(Collectors.toList());
    }


    public Optional<PickupPoint> findById(Long id) {
        return pickupPointRepository.findById(id);
    }

    public Optional<PickupPointDto> findByIdDto(Long id) {
        return pickupPointRepository.findById(id)
                .map(pickupPointMapper::toDto);
    }
    public Page<PickupPoint> getPage(Integer page, Integer size) {
        if (page == null || size == null) {
            var pickupPoints = findAll();
            if (pickupPoints.isEmpty()) {
                return Page.empty();
            }
            return new PageImpl<>(pickupPoints);
        }
        if (page < 0 || size < 1) {
            return Page.empty();
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        return pickupPointRepository.findAll(pageRequest);
    }

    public Page<PickupPointDto> getPageDto(Integer page, Integer size) {

        if (page == null || size == null) {
            var pickupPoints = findAllDto();
            if (pickupPoints.isEmpty()) {
                return Page.empty();
            }
            return new PageImpl<>(pickupPoints);
        }
        if (page < 0 || size < 1) {
            return Page.empty();
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<PickupPoint> pickupPointPage = pickupPointRepository.findAll(pageRequest);
        return pickupPointPage.map(pickupPointMapper::toDto);
    }
    
    public PickupPoint save(PickupPoint pickupPoint) {
        return pickupPointRepository.save(pickupPoint);
    }

    public PickupPointDto saveDto(PickupPointDto pickupPointDto) {
        PickupPoint pickupPoint = pickupPointMapper.toEntity(pickupPointDto);
        PickupPoint savedPickupPoint = pickupPointRepository.save(pickupPoint);
        return pickupPointMapper.toDto(savedPickupPoint);
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

    public Optional<PickupPointDto> updateDto(Long id, PickupPointDto pickupPointDto) {
        Optional<PickupPointDto> optionalSavedPickupPointDto = findByIdDto(id);

        if (optionalSavedPickupPointDto.isEmpty()) {
            return Optional.empty();
        }

        PickupPointDto savedPickupPointDto = optionalSavedPickupPointDto.get();

        if (pickupPointDto.getDirections() != null) {
            savedPickupPointDto.setDirections(pickupPointDto.getDirections());
        }
        if (pickupPointDto.getPickupPointFeatures() != null) {
            savedPickupPointDto.setPickupPointFeatures(pickupPointDto.getPickupPointFeatures());
        }
        if (pickupPointDto.getAddress() != null) {
            savedPickupPointDto.setAddress(pickupPointDto.getAddress());
        }
        if (pickupPointDto.getShelfLifeDays() != null) {
            savedPickupPointDto.setShelfLifeDays(pickupPointDto.getShelfLifeDays());
        }

        PickupPointDto updatedPickupPointDto = saveDto(savedPickupPointDto);
        return Optional.ofNullable(updatedPickupPointDto);
    }

    public Optional<PickupPoint> delete(Long id) {
        Optional<PickupPoint> optionalSavedPickupPoint = findById(id);
        if (optionalSavedPickupPoint.isPresent()) {
            pickupPointRepository.deleteById(id);
        }
        return optionalSavedPickupPoint;
    }

    public Optional<PickupPointDto> deleteDto(Long id) {
        Optional<PickupPointDto> optionalSavedPickupPointDto = findByIdDto(id);

        if (optionalSavedPickupPointDto.isPresent()) {
            pickupPointRepository.deleteById(id);
        }

        return optionalSavedPickupPointDto;
    }

}
