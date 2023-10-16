package com.gitlab.dto;


import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Setter
public class WorkingScheduleDto {

    @ReadOnlyProperty
    private Long id;

    @NotNull(message = "Day of Week should not be null. Please provide a valid day of week (e.g., MONDAY, TUESDAY, etc.).")
    private DayOfWeek dayOfWeek;

    @NotNull(message = "From Time should not be null. Please provide a valid time in HH:mm format (e.g., 09:00).")
    private LocalTime from;

    @NotNull(message = "To Time should not be null. Please provide a valid time in HH:mm format (e.g., 17:00).")
    private LocalTime to;


}
