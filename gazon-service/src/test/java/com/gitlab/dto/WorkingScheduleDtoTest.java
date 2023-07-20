package com.gitlab.dto;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class WorkingScheduleDtoTest extends AbstractDtoTest {

    @Test
    void test_valid_working_schedule() {
        WorkingScheduleDto workingScheduleDto = new WorkingScheduleDto();
        workingScheduleDto.setDayOfWeek(DayOfWeek.MONDAY);
        workingScheduleDto.setFrom(LocalTime.of(9, 0));
        workingScheduleDto.setTo(LocalTime.of(17, 0));

        assertTrue(validator.validate(workingScheduleDto).isEmpty());
    }

    @Test
    void test_invalid_day_of_week() {
        WorkingScheduleDto workingScheduleDto = new WorkingScheduleDto();
        workingScheduleDto.setDayOfWeek(null);
        workingScheduleDto.setFrom(LocalTime.of(9, 0));
        workingScheduleDto.setTo(LocalTime.of(17, 0));

        assertFalse(validator.validate(workingScheduleDto).isEmpty());
    }

    @Test
    void test_invalid_from_time() {
        WorkingScheduleDto workingScheduleDto = new WorkingScheduleDto();
        workingScheduleDto.setDayOfWeek(DayOfWeek.MONDAY);
        workingScheduleDto.setFrom(null);
        workingScheduleDto.setTo(LocalTime.of(17, 0));

        assertFalse(validator.validate(workingScheduleDto).isEmpty());
    }

    @Test
    void test_invalid_to_time() {
        WorkingScheduleDto workingScheduleDto = new WorkingScheduleDto();
        workingScheduleDto.setDayOfWeek(DayOfWeek.MONDAY);
        workingScheduleDto.setFrom(LocalTime.of(9, 0));
        workingScheduleDto.setTo(null);

        assertFalse(validator.validate(workingScheduleDto).isEmpty());
    }

    @Test
    void test_default_message_day_of_week_null() {
        WorkingScheduleDto workingScheduleDto = new WorkingScheduleDto();
        workingScheduleDto.setDayOfWeek(null);
        workingScheduleDto.setFrom(LocalTime.of(9, 0));
        workingScheduleDto.setTo(LocalTime.of(17, 0));
        String expectedMessage = "Day of Week should not be null. Please provide a valid day of week (e.g., MONDAY, TUESDAY, etc.).";
        String actualMessage = validator.validate(workingScheduleDto)
                .iterator()
                .next()
                .getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void test_default_message_from_time_null() {
        WorkingScheduleDto workingScheduleDto = new WorkingScheduleDto();
        workingScheduleDto.setDayOfWeek(DayOfWeek.MONDAY);
        workingScheduleDto.setFrom(null);
        workingScheduleDto.setTo(LocalTime.of(17, 0));
        String expectedMessage = "From Time should not be null. Please provide a valid time in HH:mm format (e.g., 09:00).";
        String actualMessage = validator.validate(workingScheduleDto)
                .iterator()
                .next()
                .getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void test_default_message_to_time_null() {
        WorkingScheduleDto workingScheduleDto = new WorkingScheduleDto();
        workingScheduleDto.setDayOfWeek(DayOfWeek.MONDAY);
        workingScheduleDto.setFrom(LocalTime.of(9, 0));
        workingScheduleDto.setTo(null);
        String expectedMessage = "To Time should not be null. Please provide a valid time in HH:mm format (e.g., 17:00).";
        String actualMessage = validator.validate(workingScheduleDto)
                .iterator()
                .next()
                .getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
