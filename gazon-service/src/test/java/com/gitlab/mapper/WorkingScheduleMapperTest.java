package com.gitlab.mapper;

import com.gitlab.dto.WorkingScheduleDto;
import com.gitlab.model.WorkingSchedule;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.time.DayOfWeek;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WorkingScheduleMapperTest {

    private final WorkingScheduleMapper mapper = Mappers.getMapper(WorkingScheduleMapper.class);

    @Test
    void should_map_working_schedule_to_Dto() {
        WorkingSchedule workingSchedule = new WorkingSchedule();
        workingSchedule.setId(1L);
        workingSchedule.setDayOfWeek(DayOfWeek.MONDAY);
        workingSchedule.setFrom(LocalTime.of(9, 0));
        workingSchedule.setTo(LocalTime.of(17, 0));

        WorkingScheduleDto actualResult = mapper.toDto(workingSchedule);

        assertNotNull(actualResult);
        assertEquals(workingSchedule.getId(), actualResult.getId());
        assertEquals(workingSchedule.getDayOfWeek(), actualResult.getDayOfWeek());
        assertEquals(workingSchedule.getFrom(), actualResult.getFrom());
        assertEquals(workingSchedule.getTo(), actualResult.getTo());
    }

    @Test
    void should_map_working_scheduleDto_to_Entity() {
        WorkingScheduleDto workingScheduleDto = new WorkingScheduleDto();
        workingScheduleDto.setId(1L);
        workingScheduleDto.setDayOfWeek(DayOfWeek.MONDAY);
        workingScheduleDto.setFrom(LocalTime.of(9, 0));
        workingScheduleDto.setTo(LocalTime.of(17, 0));

        WorkingSchedule actualResult = mapper.toEntity(workingScheduleDto);

        assertNotNull(actualResult);
        assertEquals(workingScheduleDto.getId(), actualResult.getId());
        assertEquals(workingScheduleDto.getDayOfWeek(), actualResult.getDayOfWeek());
        assertEquals(workingScheduleDto.getFrom(), actualResult.getFrom());
        assertEquals(workingScheduleDto.getTo(), actualResult.getTo());
    }
}
