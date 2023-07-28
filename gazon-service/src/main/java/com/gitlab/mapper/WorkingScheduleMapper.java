package com.gitlab.mapper;

import com.gitlab.dto.WorkingScheduleDto;
import com.gitlab.model.WorkingSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WorkingScheduleMapper {
    WorkingScheduleDto toDto(WorkingSchedule workingSchedule);

    WorkingSchedule toEntity(WorkingScheduleDto workingScheduleDto);
}
