package com.gitlab.controller;

import com.gitlab.controller.api.WorkingScheduleRestApi;
import com.gitlab.dto.WorkingScheduleDto;
import com.gitlab.model.WorkingSchedule;
import com.gitlab.service.WorkingScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class WorkingScheduleRestController implements WorkingScheduleRestApi {

    private final WorkingScheduleService workingScheduleService;

    @Override
    public ResponseEntity<List<WorkingScheduleDto>> getAll() {
        List<WorkingScheduleDto> workingSchedules = workingScheduleService.findAllDto();
        if (workingSchedules.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(workingSchedules);
        }
    }

    @Override
    public ResponseEntity<WorkingScheduleDto> get(Long id) {
        Optional<WorkingScheduleDto> optionalWorkingScheduleDto = workingScheduleService.findByIdDto(id);
        return optionalWorkingScheduleDto
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<WorkingScheduleDto> create(WorkingScheduleDto workingScheduleDto) {
        WorkingScheduleDto savedWorkingScheduleDto = workingScheduleService.saveDto(workingScheduleDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedWorkingScheduleDto);
    }

    @Override
    public ResponseEntity<WorkingScheduleDto> update(Long id, WorkingScheduleDto workingScheduleDto) {
        Optional<WorkingScheduleDto> optionalUpdatedWorkingScheduleDto = workingScheduleService.updateDto(id, workingScheduleDto);
        return optionalUpdatedWorkingScheduleDto
                .map(updatedWorkingScheduleDto -> ResponseEntity.ok(updatedWorkingScheduleDto))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        Optional<WorkingSchedule> optionalDeletedWorkingSchedule = workingScheduleService.delete(id);
        return optionalDeletedWorkingSchedule
                .map(workingSchedule -> ResponseEntity.ok().<Void>build())
                .orElse(ResponseEntity.notFound().build());
    }

}
