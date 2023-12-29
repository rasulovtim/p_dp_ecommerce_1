package com.gitlab.mapper;

import com.gitlab.dto.PickupPointDto;
import com.gitlab.model.PickupPoint;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PickupPointMapper {
    PickupPointDto toDto(PickupPoint pickupPoint);

    PickupPoint toEntity(PickupPointDto pickupPointDto);

    default List<PickupPointDto> toDtoList(List<PickupPoint> pickupPointList) {
        return pickupPointList.stream().map(this::toDto).collect(Collectors.toList());
    }

    default List<PickupPoint> toEntityList(List<PickupPointDto> pickupPointDtoList) {
        return pickupPointDtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
