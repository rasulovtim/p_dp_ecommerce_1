package com.gitlab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class WorkingScheduleDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "Day of Week should not be null. Please provide a valid day of week (e.g., MONDAY, TUESDAY, etc.).")
    private DayOfWeek dayOfWeek;

    @NotNull(message = "From Time should not be null. Please provide a valid time in HH:mm format (e.g., 09:00).")
    private LocalTime from;

    @NotNull(message = "To Time should not be null. Please provide a valid time in HH:mm format (e.g., 17:00).")
    private LocalTime to;
}
