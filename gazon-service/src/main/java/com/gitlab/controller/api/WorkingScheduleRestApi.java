package com.gitlab.controller.api;

import com.gitlab.dto.WorkingScheduleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

@Api(tags = "Working Schedule API")
@RequestMapping("/api/working-schedule")
public interface WorkingScheduleRestApi {

    @ApiOperation(value = "Get all working schedules")
    @GetMapping
    ResponseEntity<List<WorkingScheduleDto>> getAll();

    @ApiOperation(value = "Get working schedule by ID")
    @GetMapping("/{id}")
    ResponseEntity<WorkingScheduleDto> get(@ApiParam(name = "id", value = "Working Schedule ID") @PathVariable Long id);

    @ApiOperation(value = "Create a new working schedule")
    @PostMapping
    ResponseEntity<WorkingScheduleDto> create(@ApiParam(name = "workingScheduleDto", value = "Working Schedule details") @RequestBody WorkingScheduleDto workingScheduleDto);

    @ApiOperation(value = "Update working schedule by ID")
    @PutMapping("/{id}")
    ResponseEntity<WorkingScheduleDto> update(@ApiParam(name = "id", value = "Working Schedule ID") @PathVariable Long id, @ApiParam(name = "workingScheduleDto", value = "Updated Working Schedule details") @RequestBody WorkingScheduleDto workingScheduleDto);

    @ApiOperation(value = "Delete working schedule by ID")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@ApiParam(name = "id", value = "Working Schedule ID") @PathVariable Long id);
}
