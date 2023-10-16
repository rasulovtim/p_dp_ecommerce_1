package com.gitlab.service;

import com.gitlab.dto.WorkingScheduleDto;
import com.gitlab.mapper.WorkingScheduleMapper;
import com.gitlab.model.WorkingSchedule;
import com.gitlab.repository.WorkingScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkingScheduleService {

    private final WorkingScheduleRepository workingScheduleRepository;

    private final WorkingScheduleMapper workingScheduleMapper;

    public List<WorkingSchedule> findAll() {
        return workingScheduleRepository.findAll();
    }

    public List<WorkingScheduleDto> findAllDto() {
        List<WorkingSchedule> workingSchedules = workingScheduleRepository.findAll();
        return workingSchedules.stream()
                .map(workingScheduleMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<WorkingSchedule> findById(Long id) {
        return workingScheduleRepository.findById(id);
    }

    public Optional<WorkingScheduleDto> findByIdDto(Long id) {
        Optional<WorkingSchedule> optionalWorkingSchedule = workingScheduleRepository.findById(id);
        return optionalWorkingSchedule.map(workingScheduleMapper::toDto);
    }

    public WorkingSchedule save(WorkingSchedule workingSchedule) {
        if (workingSchedule == null || (workingSchedule.getDayOfWeek() == null && workingSchedule.getFrom() == null && workingSchedule.getTo() == null)) {
            throw new IllegalArgumentException("WorkingSchedule cannot be null or have all fields null");
        }
        return workingScheduleRepository.save(workingSchedule);
    }

    public WorkingScheduleDto saveDto(WorkingScheduleDto workingScheduleDto) {
        if (workingScheduleDto == null || (workingScheduleDto.getDayOfWeek() == null && workingScheduleDto.getFrom() == null && workingScheduleDto.getTo() == null)) {
            throw new IllegalArgumentException("WorkingScheduleDto cannot be null or have all fields null");
        }

        WorkingSchedule workingSchedule = workingScheduleMapper.toEntity(workingScheduleDto); // Преобразование DTO в сущность
        WorkingSchedule savedWorkingSchedule = workingScheduleRepository.save(workingSchedule);
        return workingScheduleMapper.toDto(savedWorkingSchedule); // Преобразование сохраненной сущности в DTO
    }

    public Optional<WorkingSchedule> update(Long id, WorkingSchedule workingSchedule) {
        Optional<WorkingSchedule> optionalSavedWorkingSchedule = findById(id);
        if (optionalSavedWorkingSchedule.isEmpty()) {
            return Optional.empty();
        }

        WorkingSchedule savedWorkingSchedule = optionalSavedWorkingSchedule.get();
        if (workingSchedule.getDayOfWeek() != null) {
            savedWorkingSchedule.setDayOfWeek(workingSchedule.getDayOfWeek());
        }
        if (workingSchedule.getFrom() != null) {
            savedWorkingSchedule.setFrom(workingSchedule.getFrom());
        }
        if (workingSchedule.getTo() != null) {
            savedWorkingSchedule.setTo(workingSchedule.getTo());
        }

        // Проверяем, что есть хотя бы одно не пустое поле для сохранения
        if (workingSchedule.getDayOfWeek() == null && workingSchedule.getFrom() == null && workingSchedule.getTo() == null) {
            return Optional.empty();
        }

        savedWorkingSchedule = workingScheduleRepository.save(savedWorkingSchedule);
        return Optional.of(savedWorkingSchedule);
    }

    public Optional<WorkingScheduleDto> updateDto(Long id, WorkingScheduleDto workingScheduleDto) {
        Optional<WorkingSchedule> optionalSavedWorkingSchedule = findById(id);
        if (optionalSavedWorkingSchedule.isEmpty()) {
            return Optional.empty();
        }

        WorkingSchedule savedWorkingSchedule = optionalSavedWorkingSchedule.get();
        if (workingScheduleDto.getDayOfWeek() != null) {
            savedWorkingSchedule.setDayOfWeek(workingScheduleDto.getDayOfWeek());
        }
        if (workingScheduleDto.getFrom() != null) {
            savedWorkingSchedule.setFrom(workingScheduleDto.getFrom());
        }
        if (workingScheduleDto.getTo() != null) {
            savedWorkingSchedule.setTo(workingScheduleDto.getTo());
        }

        // Проверяем, что есть хотя бы одно не пустое поле для сохранения
        if (workingScheduleDto.getDayOfWeek() == null && workingScheduleDto.getFrom() == null && workingScheduleDto.getTo() == null) {
            return Optional.empty();
        }

        savedWorkingSchedule = workingScheduleRepository.save(savedWorkingSchedule);
        WorkingScheduleDto updatedWorkingScheduleDto = workingScheduleMapper.toDto(savedWorkingSchedule); // Преобразование сохраненной сущности в DTO
        return Optional.of(updatedWorkingScheduleDto);
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

    public Optional<WorkingScheduleDto> deleteDto(Long id) {
        Optional<WorkingSchedule> optionalSavedWorkingSchedule = findById(id);
        if (optionalSavedWorkingSchedule.isEmpty()) {
            return Optional.empty();
        } else {
            WorkingSchedule savedWorkingSchedule = optionalSavedWorkingSchedule.get();
            WorkingScheduleDto workingScheduleDto = workingScheduleMapper.toDto(savedWorkingSchedule); // Преобразование сущности в DTO перед удалением
            workingScheduleRepository.deleteById(id);
            return Optional.of(workingScheduleDto);
        }
    }

    public Page<WorkingSchedule> getPage(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return workingScheduleRepository.findAll(pageRequest);
    }

    public Page<WorkingScheduleDto> getPageDto(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<WorkingSchedule> examplePage = workingScheduleRepository.findAll(pageRequest);
        return examplePage.map(workingScheduleMapper::toDto);
    }
}
