package com.gitlab.mapper;

import com.gitlab.dto.WorkingScheduleDto;
import com.gitlab.model.WorkingSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WorkingScheduleMapper {
    WorkingScheduleDto toDto(WorkingSchedule workingSchedule);

    WorkingSchedule toEntity(WorkingScheduleDto workingScheduleDto);

    default List<WorkingScheduleDto> toDtoList(List<WorkingSchedule> workingScheduleList) {
        return workingScheduleList.stream().map(this::toDto).collect(Collectors.toList());
    }

    default List<WorkingSchedule> toEntityList(List<WorkingScheduleDto> workingScheduleDtoList) {
        return workingScheduleDtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
