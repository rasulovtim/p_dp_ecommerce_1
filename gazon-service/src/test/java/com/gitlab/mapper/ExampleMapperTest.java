package com.gitlab.mapper;

import com.gitlab.dto.ExampleDto;
import com.gitlab.model.Example;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class ExampleMapperTest {

    private final ExampleMapper mapper = Mappers.getMapper(ExampleMapper.class);

    @Test
    void should_map_example_to_Dto() {
        Example example = new Example();
        example.setId(1L);
        example.setExampleText("text");

        ExampleDto actualResult = mapper.toDto(example);

        assertNotNull(actualResult);
        assertEquals(example.getId(), actualResult.getId());
        assertEquals(example.getExampleText(), actualResult.getExampleText());
    }

    @Test
    void should_map_exampleDto_to_Entity() {
        ExampleDto exampleDto = new ExampleDto();
        exampleDto.setId(1L);
        exampleDto.setExampleText("text");

        Example actualResult = mapper.toEntity(exampleDto);

        assertNotNull(actualResult);
        assertEquals(exampleDto.getId(), actualResult.getId());
        assertEquals(exampleDto.getExampleText(), actualResult.getExampleText());
    }
}