package com.gitlab.controller;

import com.gitlab.dto.WorkingScheduleDto;
import com.gitlab.mapper.WorkingScheduleMapper;
import com.gitlab.service.WorkingScheduleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WorkingScheduleRestControllerIT extends AbstractIntegrationTest {

    private static final String WORKING_SCHEDULE_URN = "/api/working-schedule";
    private static final String WORKING_SCHEDULE_URI = URL + WORKING_SCHEDULE_URN;

    @Autowired
    private WorkingScheduleService workingScheduleService;

    @Autowired
    private WorkingScheduleMapper workingScheduleMapper;

    @Test
    void should_get_all_working_schedules() throws Exception {
        String expected = objectMapper.writeValueAsString(
                workingScheduleService
                        .findAll()
                        .stream()
                        .map(workingScheduleMapper::toDto)
                        .collect(Collectors.toList())
        );

        mockMvc.perform(get(WORKING_SCHEDULE_URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_get_working_schedule_by_id() throws Exception {
        long id = 1L;
        String expected = objectMapper.writeValueAsString(
                workingScheduleMapper.toDto(
                        workingScheduleService
                                .findById(id)
                                .orElse(null))
        );

        mockMvc.perform(get(WORKING_SCHEDULE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_return_not_found_when_get_working_schedule_by_non_existent_id() throws Exception {
        long id = 10L;
        mockMvc.perform(get(WORKING_SCHEDULE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_create_working_schedule() throws Exception {
        WorkingScheduleDto workingScheduleDto = new WorkingScheduleDto();
        workingScheduleDto.setDayOfWeek(DayOfWeek.MONDAY);
        workingScheduleDto.setFrom(LocalTime.of(9, 0));
        workingScheduleDto.setTo(LocalTime.of(17, 0));
        String jsonWorkingScheduleDto = objectMapper.writeValueAsString(workingScheduleDto);

        mockMvc.perform(post(WORKING_SCHEDULE_URI)
                        .content(jsonWorkingScheduleDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void should_update_working_schedule_by_id() throws Exception {
        long id = 1L;
        long numberOfEntitiesExpected = workingScheduleService.findAll().size();

        WorkingScheduleDto workingScheduleDto = new WorkingScheduleDto();
        workingScheduleDto.setDayOfWeek(DayOfWeek.WEDNESDAY);
        workingScheduleDto.setFrom(LocalTime.of(10, 0));
        workingScheduleDto.setTo(LocalTime.of(18, 0));
        String jsonWorkingScheduleDto = objectMapper.writeValueAsString(workingScheduleDto);

        workingScheduleDto.setId(id);
        String expected = objectMapper.writeValueAsString(workingScheduleDto);

        mockMvc.perform(put(WORKING_SCHEDULE_URI + "/{id}", id)  // Заменяем patch на put
                        .content(jsonWorkingScheduleDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));

        mockMvc.perform(get(WORKING_SCHEDULE_URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(numberOfEntitiesExpected));
    }


    @Test
    void should_return_not_found_when_update_working_schedule_by_non_existent_id() throws Exception {
        long id = 10L;
        WorkingScheduleDto workingScheduleDto = new WorkingScheduleDto();
        workingScheduleDto.setDayOfWeek(DayOfWeek.WEDNESDAY);
        workingScheduleDto.setFrom(LocalTime.of(10, 0));
        workingScheduleDto.setTo(LocalTime.of(18, 0));
        String jsonWorkingScheduleDto = objectMapper.writeValueAsString(workingScheduleDto);

        mockMvc.perform(put(WORKING_SCHEDULE_URI + "/{id}", id)  // Заменяем patch на put
                        .content(jsonWorkingScheduleDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());  // Ожидаем статус 404 Not Found
    }

    @Test
    void should_delete_working_schedule_by_id() throws Exception {
        long id = 2L;
        mockMvc.perform(delete(WORKING_SCHEDULE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get(WORKING_SCHEDULE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
