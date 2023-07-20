package com.gitlab.service;

import com.gitlab.model.WorkingSchedule;
import com.gitlab.repository.WorkingScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkingScheduleService {

    private final WorkingScheduleRepository workingScheduleRepository;

    public List<WorkingSchedule> findAll() {
        return workingScheduleRepository.findAll();
    }

    public Optional<WorkingSchedule> findById(Long id) {
        return workingScheduleRepository.findById(id);
    }

    public WorkingSchedule save(WorkingSchedule workingSchedule) {
        return workingScheduleRepository.save(workingSchedule);
    }

    public Optional<WorkingSchedule> update(Long id, WorkingSchedule workingSchedule) {
        Optional<WorkingSchedule> optionalSavedWorkingSchedule = findById(id);
        WorkingSchedule savedWorkingSchedule;
        if (optionalSavedWorkingSchedule.isEmpty()) {
            return optionalSavedWorkingSchedule;
        } else {
            savedWorkingSchedule = optionalSavedWorkingSchedule.get();
        }
        if (workingSchedule.getDayOfWeek() != null) {
            savedWorkingSchedule.setDayOfWeek(workingSchedule.getDayOfWeek());
        }
        if (workingSchedule.getFrom() != null) {
            savedWorkingSchedule.setFrom(workingSchedule.getFrom());
        }
        if (workingSchedule.getTo() != null) {
            savedWorkingSchedule.setTo(workingSchedule.getTo());
        }
        return Optional.of(workingScheduleRepository.save(savedWorkingSchedule));
    }

    public Optional<WorkingSchedule> delete(Long id) {
        Optional<WorkingSchedule> optionalSavedWorkingSchedule = findById(id);
        if (optionalSavedWorkingSchedule.isEmpty()) {
            return optionalSavedWorkingSchedule;
        } else {
            workingScheduleRepository.deleteById(id);
            return optionalSavedWorkingSchedule;
        }
    }
}
