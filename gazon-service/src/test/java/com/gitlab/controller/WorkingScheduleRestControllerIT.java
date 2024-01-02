package com.gitlab.controller;

import com.gitlab.dto.WorkingScheduleDto;
import com.gitlab.mapper.WorkingScheduleMapper;
import com.gitlab.service.WorkingScheduleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
                new PageImpl<>(workingScheduleService
                        .findAll()
                        .stream()
                        .map(workingScheduleMapper::toDto)
                        .collect(Collectors.toList())
        ));

        mockMvc.perform(get(WORKING_SCHEDULE_URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));

    }
    @Test
    void should_get_page() throws Exception {
        int page = 0;
        int size = 2;
        String parameters = "?page=" + page + "&size=" + size;

        var response = workingScheduleService.getPage(page, size);
        assertFalse(response.getContent().isEmpty());

        var expected = objectMapper.writeValueAsString(new PageImpl<>(
                response.getContent().stream().map(workingScheduleMapper::toDto).toList(),
                response.getPageable(),
                response.getTotalElements()
        ));

        mockMvc.perform(get(WORKING_SCHEDULE_URI + parameters))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_get_working_schedule_by_id() throws Exception {
        WorkingScheduleDto workingScheduleDto = generateWorkingScheduleDto();
        Long id = workingScheduleDto.getId() + 3;
        workingScheduleDto.setId(id);
        String expected = objectMapper.writeValueAsString(workingScheduleDto);

        mockMvc.perform(post(WORKING_SCHEDULE_URI)
                        .content(expected)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(get(WORKING_SCHEDULE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));

        mockMvc.perform(delete(WORKING_SCHEDULE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void should_return_not_found_when_get_working_schedule_by_non_existent_id() throws Exception {
        long id = 10000L;
        mockMvc.perform(get(WORKING_SCHEDULE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    void should_create_working_schedule() throws Exception {
        WorkingScheduleDto workingScheduleDto = generateWorkingScheduleDto();
        Long id = workingScheduleDto.getId() + 2;
        workingScheduleDto.setId(id);
        String jsonWorkingScheduleDto = objectMapper.writeValueAsString(workingScheduleDto);

        mockMvc.perform(post(WORKING_SCHEDULE_URI)
                        .content(jsonWorkingScheduleDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(delete(WORKING_SCHEDULE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get(WORKING_SCHEDULE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_not_created_working_schedule_when_in_DB_exists_working_schedule_with_same_fields() throws Exception {
        WorkingScheduleDto workingScheduleDto = generateWorkingScheduleDto();
        Long id = workingScheduleDto.getId() + 1;
        workingScheduleDto.setId(id);
        String jsonWorkingScheduleDto = objectMapper.writeValueAsString(workingScheduleDto);

        mockMvc.perform(post(WORKING_SCHEDULE_URI)
                        .content(jsonWorkingScheduleDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(post(WORKING_SCHEDULE_URI)
                        .content(jsonWorkingScheduleDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(delete(WORKING_SCHEDULE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get(WORKING_SCHEDULE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    void should_update_working_schedule_by_id() throws Exception {

        WorkingScheduleDto workingScheduleDto = generateWorkingScheduleDto();
        Long id = workingScheduleDto.getId();
        String jsonWorkingScheduleDto = objectMapper.writeValueAsString(workingScheduleDto);


        mockMvc.perform(post(WORKING_SCHEDULE_URI)
                        .content(jsonWorkingScheduleDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        workingScheduleDto.setDayOfWeek(DayOfWeek.THURSDAY);
        workingScheduleDto.setFrom(LocalTime.parse("05:05"));
        workingScheduleDto.setTo(LocalTime.parse("06:06"));
        jsonWorkingScheduleDto = objectMapper.writeValueAsString(workingScheduleDto);

        mockMvc.perform(patch(WORKING_SCHEDULE_URI + "/{id}", id)
                        .content(jsonWorkingScheduleDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonWorkingScheduleDto));

        mockMvc.perform(delete(WORKING_SCHEDULE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get(WORKING_SCHEDULE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    void should_return_not_found_when_update_working_schedule_by_non_existent_id() throws Exception {
        long id = 10000L;
        WorkingScheduleDto workingScheduleDto = generateWorkingScheduleDto();
        String jsonWorkingScheduleDto = objectMapper.writeValueAsString(workingScheduleDto);

        mockMvc.perform(patch(WORKING_SCHEDULE_URI + "/{id}", id)
                        .content(jsonWorkingScheduleDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    void should_delete_working_schedule_by_id() throws Exception {
        WorkingScheduleDto workingScheduleDto = generateWorkingScheduleDto();
        Long id = workingScheduleDto.getId() + 4;
        workingScheduleDto.setId(id);
        String jsonWorkingScheduleDto = objectMapper.writeValueAsString(workingScheduleDto);

        mockMvc.perform(post(WORKING_SCHEDULE_URI)
                        .content(jsonWorkingScheduleDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(delete(WORKING_SCHEDULE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get(WORKING_SCHEDULE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private WorkingScheduleDto generateWorkingScheduleDto() {
        WorkingScheduleDto workingScheduleDto = new WorkingScheduleDto();
        workingScheduleDto.setId(4L);
        workingScheduleDto.setFrom(LocalTime.of(10, 0));
        workingScheduleDto.setTo(LocalTime.of(18, 0));
        workingScheduleDto.setDayOfWeek(DayOfWeek.WEDNESDAY);

        return workingScheduleDto;
    }
}