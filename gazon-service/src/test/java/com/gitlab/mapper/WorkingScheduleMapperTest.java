package com.gitlab.mapper;

import com.gitlab.dto.WorkingScheduleDto;
import com.gitlab.model.WorkingSchedule;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WorkingScheduleMapperTest {

    private final WorkingScheduleMapper mapper = Mappers.getMapper(WorkingScheduleMapper.class);

    @Test
    void should_map_working_schedule_to_Dto() {
        WorkingSchedule workingSchedule = getWorkingSchedule(1L);

        WorkingScheduleDto actualResult = mapper.toDto(workingSchedule);

        assertNotNull(actualResult);
        assertEquals(workingSchedule.getId(), actualResult.getId());
        assertEquals(workingSchedule.getDayOfWeek(), actualResult.getDayOfWeek());
        assertEquals(workingSchedule.getFrom(), actualResult.getFrom());
        assertEquals(workingSchedule.getTo(), actualResult.getTo());
    }

    @Test
    void should_map_working_scheduleDto_to_Entity() {
        WorkingScheduleDto workingScheduleDto = getWorkingScheduleDto(1L);

        WorkingSchedule actualResult = mapper.toEntity(workingScheduleDto);

        assertNotNull(actualResult);
        assertEquals(workingScheduleDto.getId(), actualResult.getId());
        assertEquals(workingScheduleDto.getDayOfWeek(), actualResult.getDayOfWeek());
        assertEquals(workingScheduleDto.getFrom(), actualResult.getFrom());
        assertEquals(workingScheduleDto.getTo(), actualResult.getTo());
    }


    @Test
    void should_map_workingScheduleList_to_DtoList() {
        List<WorkingSchedule> workingScheduleList = List.of(getWorkingSchedule(1L), getWorkingSchedule(2L), getWorkingSchedule(3L));

        List<WorkingScheduleDto> workingScheduleDtoList = mapper.toDtoList(workingScheduleList);

        assertNotNull(workingScheduleDtoList);
        assertEquals(workingScheduleList.size(), workingScheduleList.size());
        for (int i = 0; i < workingScheduleDtoList.size(); i++) {
            WorkingScheduleDto dto = workingScheduleDtoList.get(i);
            WorkingSchedule entity = workingScheduleList.get(i);
            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getDayOfWeek(), entity.getDayOfWeek());
            assertEquals(dto.getFrom(), entity.getFrom());
            assertEquals(dto.getTo(), entity.getTo());
        }
    }

    @Test
    void should_map_workingScheduleDtoList_to_EntityList() {
        List<WorkingScheduleDto> workingScheduleDtoList = List.of(getWorkingScheduleDto(1L), getWorkingScheduleDto(2L), getWorkingScheduleDto(3L));

        List<WorkingSchedule> workingScheduleList = mapper.toEntityList(workingScheduleDtoList);

        assertNotNull(workingScheduleList);
        assertEquals(workingScheduleList.size(), workingScheduleList.size());
        for (int i = 0; i < workingScheduleList.size(); i++) {
            WorkingScheduleDto dto = workingScheduleDtoList.get(i);
            WorkingSchedule entity = workingScheduleList.get(i);
            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getDayOfWeek(), entity.getDayOfWeek());
            assertEquals(dto.getFrom(), entity.getFrom());
            assertEquals(dto.getTo(), entity.getTo());
        }
    }

    @NotNull
    private WorkingSchedule getWorkingSchedule(Long id) {
        WorkingSchedule workingSchedule = new WorkingSchedule();
        workingSchedule.setId(id);
        workingSchedule.setDayOfWeek(DayOfWeek.MONDAY);
        workingSchedule.setFrom(LocalTime.of(9, (int) (1 + id)));
        workingSchedule.setTo(LocalTime.of(17, (int) (1 + id)));

        return workingSchedule;
    }

    @NotNull
    private WorkingScheduleDto getWorkingScheduleDto(Long id) {
        WorkingScheduleDto workingScheduleDto = new WorkingScheduleDto();
        workingScheduleDto.setId(id);
        workingScheduleDto.setDayOfWeek(DayOfWeek.MONDAY);
        workingScheduleDto.setFrom(LocalTime.of(9, (int) (id + 1)));
        workingScheduleDto.setTo(LocalTime.of(17, (int) (id + 1)));
        return workingScheduleDto;
    }
}
