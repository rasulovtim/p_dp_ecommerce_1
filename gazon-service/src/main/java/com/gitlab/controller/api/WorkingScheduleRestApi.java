package com.gitlab.controller.api;

import com.gitlab.dto.WorkingScheduleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
public interface WorkingScheduleRestApi {

    @Operation(summary = "Get all working schedules")
    @GetMapping("/working-schedule")
    ResponseEntity<List<WorkingScheduleDto>> getAll();

    @Operation(summary = "Get working schedule by ID")
    @GetMapping("/working-schedule/{id}")
    ResponseEntity<WorkingScheduleDto> get(@PathVariable Long id);

    @Operation(summary = "Create a new working schedule")
    @PostMapping("/working-schedule")
    ResponseEntity<WorkingScheduleDto> create(@RequestBody WorkingScheduleDto workingScheduleDto);

    @Operation(summary = "Update working schedule by ID")
    @PutMapping("/working-schedule/{id}")
    ResponseEntity<WorkingScheduleDto> update(@PathVariable Long id, @RequestBody WorkingScheduleDto workingScheduleDto);

    @Operation(summary = "Delete working schedule by ID")
    @DeleteMapping("/working-schedule/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
