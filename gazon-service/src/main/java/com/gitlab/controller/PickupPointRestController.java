package com.gitlab.controller;

import com.gitlab.controller.api.PickupPointRestApi;
import com.gitlab.dto.PickupPointDto;
import com.gitlab.mapper.PickupPointMapper;
import com.gitlab.service.PickupPointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class PickupPointRestController implements PickupPointRestApi {

    private final PickupPointService pickupPointService;

    private final PickupPointMapper pickupPointMapper;

    @Override
    public ResponseEntity<List<PickupPointDto>> getAll() {
        var pickupPoints = pickupPointService.findAll();
        return pickupPoints.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(pickupPoints.stream().map(pickupPointMapper::toDto).toList());
    }

    @Override
    public ResponseEntity<PickupPointDto> get(Long id) {
        return pickupPointService.findById(id)
                .map(value -> ResponseEntity.ok(pickupPointMapper.toDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<PickupPointDto> create(PickupPointDto pickupPointDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pickupPointMapper
                        .toDto(pickupPointService
                                .save(pickupPointMapper
                                        .toEntity(pickupPointDto))));
    }

    @Override
    public ResponseEntity<PickupPointDto> update(Long id, PickupPointDto pickupPointDto) {
        return pickupPointService.update(id, pickupPointMapper.toEntity(pickupPointDto))
                .map(value -> ResponseEntity.ok(pickupPointMapper.toDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        return pickupPointService.delete(id).isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok().build();
    }
}
