package com.gitlab.controllers.api.rest;

import com.gitlab.dto.WorkingScheduleDto;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.validation.Valid;

@Api(tags = "WorkingSchedule REST")
@Tag(name = "WorkingSchedule REST", description = "API WorkingSchedule description")
public interface WorkingScheduleRestApi {

    @GetMapping("/api/working-schedule")
    @ApiOperation(value = "Get Page of Working Schedules")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Working Schedules Page found"),
            @ApiResponse(code = 204, message = "Working Schedules not present")}
    )
    ResponseEntity<Page<WorkingScheduleDto>> getPage(@ApiParam(name = "page") @RequestParam(required = false, value = "page") Integer page,
                                                     @ApiParam(name = "size") @RequestParam(required = false, value = "size") Integer size);

    @GetMapping("/api/working-schedule/{id}")
    @ApiOperation(value = "Get Working Schedule by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Working Schedule found"),
            @ApiResponse(code = 404, message = "Working Schedule not found")}
    )
    ResponseEntity<WorkingScheduleDto> get(@ApiParam(name = "id", value = "WorkingSchedule.id") @PathVariable(value = "id") Long id);

    @PostMapping("/api/working-schedule")
    @ApiOperation(value = "Create Working Schedule")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Working Schedule created"),
            @ApiResponse(code = 400, message = "Working Schedule not created")}
    )
    ResponseEntity<WorkingScheduleDto> create(@ApiParam(name = "workingSchedule", value = "WorkingScheduleDto") @Valid @RequestBody WorkingScheduleDto workingScheduleDto);

    @PatchMapping("/api/working-schedule/{id}")
    @ApiOperation(value = "Update Working Schedule")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Working Schedule updated"),
            @ApiResponse(code = 400, message = "Working Schedule not updated")}
    )
    ResponseEntity<WorkingScheduleDto> update(@ApiParam(name = "id", value = "WorkingSchedule.id") @PathVariable(value = "id") Long id,
                                              @ApiParam(name = "workingSchedule", value = "WorkingScheduleDto") @Valid @RequestBody WorkingScheduleDto workingScheduleDto);

    @DeleteMapping("/api/working-schedule/{id}")
    @ApiOperation(value = "Delete Example by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Working Schedule deleted"),
            @ApiResponse(code = 404, message = "Working Schedule not found")}
    )
    ResponseEntity<Void> delete(@ApiParam(name = "id", value = "WorkingSchedule.id") @PathVariable(value = "id") Long id);
}
