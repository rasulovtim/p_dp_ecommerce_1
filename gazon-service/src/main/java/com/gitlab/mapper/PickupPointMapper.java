package com.gitlab.mapper;

import com.gitlab.dto.PickupPointDto;
import com.gitlab.model.PickupPoint;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PickupPointMapper {

    PickupPointDto toDto(PickupPoint pickupPoint);

    PickupPoint toEntity(PickupPointDto pickupPointDto);

    List<PickupPointDto> toDtoList(List<PickupPoint> pickupPointList);

    List<PickupPoint> toEntityList(List<PickupPointDto> pickupPointDtoList);
}