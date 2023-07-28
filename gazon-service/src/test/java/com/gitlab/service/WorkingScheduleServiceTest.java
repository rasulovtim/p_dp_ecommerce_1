package com.gitlab.service;

import com.gitlab.model.WorkingSchedule;
import com.gitlab.repository.WorkingScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkingScheduleServiceTest {

    @Mock
    private WorkingScheduleRepository workingScheduleRepository;

    @InjectMocks
    private WorkingScheduleService workingScheduleService;

    @Test
    void should_find_all_working_schedules() {
        List<WorkingSchedule> expectedResult = generateWorkingSchedules();
        when(workingScheduleRepository.findAll()).thenReturn(generateWorkingSchedules());

        List<WorkingSchedule> actualResult = workingScheduleService.findAll();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_find_working_schedule_by_id() {
        long id = 1L;
        WorkingSchedule expectedResult = generateWorkingSchedule();
        when(workingScheduleRepository.findById(id)).thenReturn(Optional.of(expectedResult));

        Optional<WorkingSchedule> actualResult = workingScheduleService.findById(id);

        assertEquals(expectedResult, actualResult.orElse(null));
    }

    @Test
    void should_save_working_schedule() {
        WorkingSchedule expectedResult = generateWorkingSchedule();
        when(workingScheduleRepository.save(expectedResult)).thenReturn(expectedResult);

        WorkingSchedule actualResult = workingScheduleService.save(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_update_working_schedule_day_of_week() {
        long id = 1L;
        WorkingSchedule workingScheduleToUpdate = new WorkingSchedule();
        workingScheduleToUpdate.setDayOfWeek(DayOfWeek.WEDNESDAY);

        WorkingSchedule workingScheduleBeforeUpdate = new WorkingSchedule(id, DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0));
        WorkingSchedule updatedWorkingSchedule = new WorkingSchedule(id, DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0));

        when(workingScheduleRepository.findById(id)).thenReturn(Optional.of(workingScheduleBeforeUpdate));
        when(workingScheduleRepository.save(updatedWorkingSchedule)).thenReturn(updatedWorkingSchedule);

        Optional<WorkingSchedule> actualResult = workingScheduleService.update(id, workingScheduleToUpdate);

        assertEquals(updatedWorkingSchedule, actualResult.orElse(null));
        verify(workingScheduleRepository).save(updatedWorkingSchedule);
    }

    @Test
    void should_update_working_schedule_from_time() {
        long id = 1L;
        WorkingSchedule workingScheduleToUpdate = new WorkingSchedule();
        workingScheduleToUpdate.setFrom(LocalTime.of(10, 0));

        WorkingSchedule workingScheduleBeforeUpdate = new WorkingSchedule(id, DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0));
        WorkingSchedule updatedWorkingSchedule = new WorkingSchedule(id, DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(17, 0));

        when(workingScheduleRepository.findById(id)).thenReturn(Optional.of(workingScheduleBeforeUpdate));
        when(workingScheduleRepository.save(updatedWorkingSchedule)).thenReturn(updatedWorkingSchedule);

        Optional<WorkingSchedule> actualResult = workingScheduleService.update(id, workingScheduleToUpdate);

        assertEquals(updatedWorkingSchedule, actualResult.orElse(null));
        verify(workingScheduleRepository).save(updatedWorkingSchedule);
    }

    @Test
    void should_update_working_schedule_to_time() {
        long id = 1L;
        WorkingSchedule workingScheduleToUpdate = new WorkingSchedule();
        workingScheduleToUpdate.setTo(LocalTime.of(18, 0));

        WorkingSchedule workingScheduleBeforeUpdate = new WorkingSchedule(id, DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0));
        WorkingSchedule updatedWorkingSchedule = new WorkingSchedule(id, DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0));

        when(workingScheduleRepository.findById(id)).thenReturn(Optional.of(workingScheduleBeforeUpdate));
        when(workingScheduleRepository.save(updatedWorkingSchedule)).thenReturn(updatedWorkingSchedule);

        Optional<WorkingSchedule> actualResult = workingScheduleService.update(id, workingScheduleToUpdate);

        assertEquals(updatedWorkingSchedule, actualResult.orElse(null));
        verify(workingScheduleRepository).save(updatedWorkingSchedule);
    }

    @Test
    void should_not_update_working_schedule_with_all_fields_null() {
        long id = 1L;
        WorkingSchedule workingScheduleToUpdate = new WorkingSchedule();
        workingScheduleToUpdate.setDayOfWeek(null);
        workingScheduleToUpdate.setFrom(null);
        workingScheduleToUpdate.setTo(null);

        WorkingSchedule workingScheduleBeforeUpdate = new WorkingSchedule(id, DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0));

        when(workingScheduleRepository.findById(id)).thenReturn(Optional.of(workingScheduleBeforeUpdate));

        Optional<WorkingSchedule> actualResult = workingScheduleService.update(id, workingScheduleToUpdate);

        verify(workingScheduleRepository, never()).save(any());
        assertFalse(actualResult.isPresent());
    }

    @Test
    void should_not_update_working_schedule_when_entity_not_found() {
        long id = 1L;
        WorkingSchedule workingScheduleToUpdate = new WorkingSchedule();
        workingScheduleToUpdate.setDayOfWeek(DayOfWeek.WEDNESDAY);
        workingScheduleToUpdate.setFrom(LocalTime.of(10, 0));
        workingScheduleToUpdate.setTo(LocalTime.of(18, 0));

        when(workingScheduleRepository.findById(id)).thenReturn(Optional.empty());

        Optional<WorkingSchedule> actualResult = workingScheduleService.update(id, workingScheduleToUpdate);

        verify(workingScheduleRepository, never()).save(any());
        assertFalse(actualResult.isPresent());
    }

    @Test
    void should_delete_working_schedule() {
        long id = 1L;
        when(workingScheduleRepository.findById(id)).thenReturn(Optional.of(generateWorkingSchedule()));

        workingScheduleService.delete(id);

        verify(workingScheduleRepository).deleteById(id);
    }

    @Test
    void should_not_delete_working_schedule_when_entity_not_found() {
        long id = 1L;
        when(workingScheduleRepository.findById(id)).thenReturn(Optional.empty());

        workingScheduleService.delete(id);

        verify(workingScheduleRepository, never()).deleteById(anyLong());
    }

    private List<WorkingSchedule> generateWorkingSchedules() {
        return List.of(
                new WorkingSchedule(1L, DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)),
                new WorkingSchedule(2L, DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)),
                new WorkingSchedule(3L, DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)),
                new WorkingSchedule(4L, DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)),
                new WorkingSchedule(5L, DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))
        );
    }

    private WorkingSchedule generateWorkingSchedule() {
        return new WorkingSchedule(1L, DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0));
    }
}