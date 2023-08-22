package com.gitlab.controller;

import com.gitlab.controller.api.PickupPointRestApi;
import com.gitlab.dto.PickupPointDto;
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

    @Override
    public ResponseEntity<List<PickupPointDto>> getAll() {
        List<PickupPointDto> pickupPointDtos = pickupPointService.findAllDto();

        if (pickupPointDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(pickupPointDtos);
        }
    }

    @Override
    public ResponseEntity<PickupPointDto> get(Long id) {
        return pickupPointService.findByIdDto(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<PickupPointDto> create(PickupPointDto pickupPointDto) {
        PickupPointDto createdPickupPointDto = pickupPointService.saveDto(pickupPointDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPickupPointDto);
    }

    @Override
    public ResponseEntity<PickupPointDto> update(Long id, PickupPointDto pickupPointDto) {
        return pickupPointService.updateDto(id, pickupPointDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        boolean deleted = pickupPointService.deleteDto(id).isPresent();

        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}