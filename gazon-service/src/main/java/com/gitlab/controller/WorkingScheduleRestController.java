package com.gitlab.controller;

import com.gitlab.controller.api.WorkingScheduleRestApi;
import com.gitlab.dto.WorkingScheduleDto;
import com.gitlab.mapper.WorkingScheduleMapper;
import com.gitlab.model.WorkingSchedule;
import com.gitlab.service.WorkingScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class WorkingScheduleRestController implements WorkingScheduleRestApi {

    private final WorkingScheduleService workingScheduleService;
    private final WorkingScheduleMapper workingScheduleMapper;

    @Override
    public ResponseEntity<List<WorkingScheduleDto>> getAll() {
        var workingSchedules = workingScheduleService.findAll();
        if (workingSchedules.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(workingSchedules.stream().map(workingScheduleMapper::toDto).toList());
        }
    }

    @Override
    public ResponseEntity<WorkingScheduleDto> get(Long id) {
        Optional<WorkingSchedule> optionalWorkingSchedule = workingScheduleService.findById(id);
        return optionalWorkingSchedule
                .map(value -> ResponseEntity.ok(workingScheduleMapper.toDto(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<WorkingScheduleDto> create(WorkingScheduleDto workingScheduleDto) {
        WorkingSchedule workingSchedule = workingScheduleService.save(workingScheduleMapper.toEntity(workingScheduleDto));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(workingScheduleMapper.toDto(workingSchedule));
    }

    @Override
    public ResponseEntity<WorkingScheduleDto> update(Long id, WorkingScheduleDto workingScheduleDto) {
        Optional<WorkingSchedule> optionalWorkingSchedule = workingScheduleService.findById(id);
        if (optionalWorkingSchedule.isPresent()) {
            WorkingSchedule existingWorkingSchedule = optionalWorkingSchedule.get();
            DayOfWeek dayOfWeek = workingScheduleDto.getDayOfWeek();
            LocalTime from = workingScheduleDto.getFrom();
            LocalTime to = workingScheduleDto.getTo();

            // Обновляем только те поля, которые присутствуют в запросе
            if (dayOfWeek != null) {
                existingWorkingSchedule.setDayOfWeek(dayOfWeek);
            }
            if (from != null) {
                existingWorkingSchedule.setFrom(from);
            }
            if (to != null) {
                existingWorkingSchedule.setTo(to);
            }

            WorkingSchedule updatedWorkingSchedule = workingScheduleService.save(existingWorkingSchedule);
            return ResponseEntity.ok(workingScheduleMapper.toDto(updatedWorkingSchedule));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Override
    public ResponseEntity<Void> delete(Long id) {
        Optional<WorkingSchedule> optionalWorkingSchedule = workingScheduleService.findById(id);
        if (optionalWorkingSchedule.isPresent()) {
            workingScheduleService.delete(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
